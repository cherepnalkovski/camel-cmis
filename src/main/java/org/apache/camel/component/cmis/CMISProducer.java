/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.cmis;

import java.util.*;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.NoSuchHeaderException;
import org.apache.camel.RuntimeExchangeException;
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.util.ExchangeHelper;
import org.apache.camel.util.MessageHelper;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisBaseException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

import javax.print.Doc;

/**
 * The CMIS producer.
 */
public class CMISProducer extends DefaultProducer {

    private final CMISSessionFacadeFactory sessionFacadeFactory;
    private CMISSessionFacade sessionFacade;

    public CMISProducer(CMISEndpoint endpoint, CMISSessionFacadeFactory sessionFacadeFactory) {
        super(endpoint);
        this.sessionFacadeFactory = sessionFacadeFactory;
        this.sessionFacade = null;
    }

    @Override
    public CMISEndpoint getEndpoint() {
        return (CMISEndpoint) super.getEndpoint();
    }

    public void process(Exchange exchange) throws Exception {

        CamelCMISActions action = exchange.getIn().getHeader(CamelCMISConstants.CMIS_ACTION, CamelCMISActions.class);

        this.getClass().getDeclaredMethod(action.getMethodName(), Exchange.class).invoke(this , exchange);

    }

    private Map<String, Object> filterTypeProperties(Map<String, Object> properties) throws Exception {
        Map<String, Object> result = new HashMap<>(properties.size());

        String objectTypeName = CamelCMISConstants.CMIS_DOCUMENT;
        if (properties.containsKey(PropertyIds.OBJECT_TYPE_ID)) {
            objectTypeName = (String) properties.get(PropertyIds.OBJECT_TYPE_ID);
        }

        Set<String> types = new HashSet<>();
        types.addAll(getSessionFacade().getPropertiesFor(objectTypeName));

        if (getSessionFacade().supportsSecondaries() && properties.containsKey(PropertyIds.SECONDARY_OBJECT_TYPE_IDS)) {
            @SuppressWarnings("unchecked")
            Collection<String> secondaryTypes = (Collection<String>) properties.get(PropertyIds.SECONDARY_OBJECT_TYPE_IDS);
            for (String secondaryType : secondaryTypes) {
                types.addAll(getSessionFacade().getPropertiesFor(secondaryType));
            }
        }

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            if (types.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private CmisObject createNode(Exchange exchange) throws Exception {
        validateRequiredHeader(exchange, PropertyIds.NAME);

        Message message = exchange.getIn();
        String parentFolderPath = parentFolderPathFor(message);
        Folder parentFolder = getFolderOnPath(exchange, parentFolderPath);
        Map<String, Object> cmisProperties = filterTypeProperties(message.getHeaders());

        if (isDocument(exchange)) {
            String fileName = message.getHeader(PropertyIds.NAME, String.class);
            String mimeType = getMimeType(message);
            byte[] buf = getBodyData(message);
            ContentStream contentStream = getSessionFacade().createContentStream(fileName, buf, mimeType);
            return storeDocument(parentFolder, cmisProperties, contentStream);
        } else if (isFolder(message)) {
            return storeFolder(parentFolder, cmisProperties);
        } else { // other types
            return storeDocument(parentFolder, cmisProperties, null);
        }
    }

    private List<String> deleteFolder(Exchange exchange) throws Exception {
        validateRequiredHeader(exchange, CamelCMISConstants.CMIS_FOLDER_PATH);

        Message message = exchange.getIn();

        String path = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);
        Folder folder = getFolderOnPath(exchange, path);
        return folder.deleteTree(true, UnfileObject.DELETE, true);
    }

    private void deleteDocument(Exchange exchange) throws Exception {
        validateRequiredHeader(exchange, CamelCMISConstants.CMIS_DOCUMENT_PATH);

        Message message = exchange.getIn();

        String path = message.getHeader(CamelCMISConstants.CMIS_DOCUMENT_PATH, String.class);
        Document document = getDocumentOnPath(exchange, path);

        document.deleteAllVersions();
    }

    private void moveDocument(Exchange exchange) throws Exception
    {
        Message message = exchange.getIn();

        String destinationFolderPath = message.getHeader(CamelCMISConstants.CMIS_DESTIONATION_FOLDER_PATH, String.class);
        String sourceFolderPath = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);
        String path = message.getHeader(CamelCMISConstants.CMIS_DOCUMENT_PATH, String.class);


        Folder sourceFolder = getFolderOnPath(exchange, sourceFolderPath);
        Folder targetFolder = getFolderOnPath(exchange, destinationFolderPath);

        Document document = getDocumentOnPath(exchange, path);

        if(document != null)
        {
            if(document.getAllowableActions().getAllowableActions().contains(Action.CAN_MOVE_OBJECT) == false)
            {
                throw new CmisUnauthorizedException("Current user does not have permission to move " + path + document.getName());
            }

            try {
                document.move(sourceFolder, targetFolder);
                log.info("Moved document from " + path + " to " + destinationFolderPath );
            }
            catch (CmisRuntimeException e)
            {
                log.error("Cannot move document to folder " + destinationFolderPath + " : " + e.getMessage());
            }
        }
        else
        {
            log.error("Document is null, cannot move!");
        }
    }

    private void moveFolder(Exchange exchange) throws Exception {
        Message message = exchange.getIn();

        String destinationFolderPath = message.getHeader(CamelCMISConstants.CMIS_DESTIONATION_FOLDER_PATH, String.class);
        String sourceFolderPath = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);
        String path = message.getHeader(CamelCMISConstants.CMIS_DOCUMENT_PATH, String.class);


        Folder sourceFolder = getFolderOnPath(exchange, sourceFolderPath);
        Folder targetFolder = getFolderOnPath(exchange, destinationFolderPath);

        if(sourceFolder != null)
        {
            if(sourceFolder.getAllowableActions().getAllowableActions().contains(Action.CAN_MOVE_OBJECT) == false)
            {
                throw new CmisUnauthorizedException("Current user does not have permission to move " + path + sourceFolder.getName());
            }

            try {
                sourceFolder.move(sourceFolder, targetFolder);
                log.info("Moved Folder from " + path + " to " + destinationFolderPath );
            }
            catch (CmisRuntimeException e)
            {
                log.error("Cannot move Folder to " + destinationFolderPath + " : " + e.getMessage());
            }
        }
        else
        {
            log.error("Folder is null, cannot move!");
        }
    }

    private void copyDocument(Exchange exchange) throws Exception {
        Message message = exchange.getIn();

        String destinationFolderPath = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);
        String documentPath = message.getHeader(CamelCMISConstants.CMIS_DOCUMENT_PATH, String.class);
        Folder destinationFolder = getFolderOnPath(exchange, destinationFolderPath);

        Document document = getDocumentOnPath(exchange, documentPath);

        document.copy(destinationFolder);
    }

    private void copyFolder(Exchange exchange) throws Exception
    {
        Message message = exchange.getIn();

        String destinationFolderPath = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);
        String sourceFolderPath = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);

        Folder destinationFolder = getFolderOnPath(exchange, destinationFolderPath);
        Folder toCopyFolder = getFolderOnPath(exchange, sourceFolderPath);

        copyFolderRecursive(destinationFolder, toCopyFolder);
    }

    public void copyFolderRecursive(Folder destinationFolder, Folder toCopyFolder)
    {
        Map<String, Object> folderProperties = new HashMap<String, Object>();
        folderProperties.put(PropertyIds.NAME, toCopyFolder.getName());
        folderProperties.put(PropertyIds.OBJECT_TYPE_ID, toCopyFolder.getBaseTypeId().value());
        Folder newFolder = destinationFolder.createFolder(folderProperties);
        copyChildren(newFolder, toCopyFolder);
    }

    public void copyChildren(Folder destinationFolder, Folder toCopyFolder)
    {
        ItemIterable<CmisObject> immediateChildren = toCopyFolder.getChildren();
        for (CmisObject child : immediateChildren)
        {
            if (child instanceof Document)
            {
                ((Document) child).copy(destinationFolder);
            } else if (child instanceof Folder)
            {
                copyFolderRecursive(destinationFolder, (Folder) child);
            }
        }
    }

    private void renameFolder(Exchange exchange) throws Exception
    {
        Message message = exchange.getIn();

        String newName = message.getHeader(PropertyIds.NAME, String.class);
        String documentPath = message.getHeader(CamelCMISConstants.CMIS_DOCUMENT_PATH, String.class);
        CmisObject cmisObject = getDocumentOnPath(exchange, documentPath);

        cmisObject.rename(newName);
    }

    private void renameDocument(Exchange exchange) throws Exception
    {
        Message message = exchange.getIn();

        String newName = message.getHeader(PropertyIds.NAME, String.class);
        String folderPath = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);
        CmisObject cmisObject = getFolderOnPath(exchange, folderPath);

        cmisObject.rename(newName);
    }

    private Folder getFolderOnPath(Exchange exchange, String path) throws Exception {
        try {
            return (Folder) getSessionFacade().getObjectByPath(path);
        } catch (CmisObjectNotFoundException e) {
            throw new RuntimeExchangeException("Path not found " + path, exchange, e);
        }
    }

    private Document getDocumentOnPath(Exchange exchange, String path) throws Exception {
        try {
            return (Document) getSessionFacade().getObjectByPath(path);
        } catch (CmisObjectNotFoundException e) {
            throw new RuntimeExchangeException("Path not found " + path, exchange, e);
        }
    }

    private String parentFolderPathFor(Message message) throws Exception {
        String customPath = message.getHeader(CamelCMISConstants.CMIS_FOLDER_PATH, String.class);
        if (customPath != null) {
            return customPath;
        }

        if (isFolder(message)) {
            String path = (String) message.getHeader(PropertyIds.PATH);
            String name = (String) message.getHeader(PropertyIds.NAME);
            if (path != null && path.length() > name.length()) {
                return path.substring(0, path.length() - name.length());
            }
        }

        return "/";
    }

    private boolean isFolder(Message message) throws Exception {
        String baseTypeId = message.getHeader(PropertyIds.OBJECT_TYPE_ID, String.class);
        if (baseTypeId != null) {
            return CamelCMISConstants.CMIS_FOLDER.equals(getSessionFacade().getCMISTypeFor(baseTypeId));
        }
        return message.getBody() == null;
    }

    private Folder storeFolder(Folder parentFolder, Map<String, Object> cmisProperties) throws Exception {
        if (!cmisProperties.containsKey(PropertyIds.OBJECT_TYPE_ID)) {
            cmisProperties.put(PropertyIds.OBJECT_TYPE_ID, CamelCMISConstants.CMIS_FOLDER);
        }
        log.debug("Creating folder with properties: {}", cmisProperties);
        return parentFolder.createFolder(cmisProperties);
    }

    private Document storeDocument(Folder parentFolder, Map<String, Object> cmisProperties, ContentStream contentStream) throws Exception {
        if (!cmisProperties.containsKey(PropertyIds.OBJECT_TYPE_ID)) {
            cmisProperties.put(PropertyIds.OBJECT_TYPE_ID, CamelCMISConstants.CMIS_DOCUMENT);
        }

        VersioningState versioningState = VersioningState.NONE;
        if (getSessionFacade().isObjectTypeVersionable((String) cmisProperties.get(PropertyIds.OBJECT_TYPE_ID))) {
            versioningState = VersioningState.MAJOR;
        }
        log.debug("Creating document with properties: {}", cmisProperties);
        return parentFolder.createDocument(cmisProperties, contentStream, versioningState);
    }

    private void validateRequiredHeader(Exchange exchange, String name) throws NoSuchHeaderException {
        ExchangeHelper.getMandatoryHeader(exchange, name, String.class);
    }

    private boolean isDocument(Exchange exchange) throws Exception {
        String baseTypeId = exchange.getIn().getHeader(PropertyIds.OBJECT_TYPE_ID, String.class);
        if (baseTypeId != null) {
            return CamelCMISConstants.CMIS_DOCUMENT.equals(getSessionFacade().getCMISTypeFor(baseTypeId));
        }
        return exchange.getIn().getBody() != null;
    }

    private byte[] getBodyData(Message message) {
        return message.getBody(byte[].class);
    }

    private String getMimeType(Message message) throws NoSuchHeaderException {
        String mimeType = message.getHeader(PropertyIds.CONTENT_STREAM_MIME_TYPE, String.class);
        if (mimeType == null) {
            mimeType = MessageHelper.getContentType(message);
        }
        return mimeType;
    }

    private CMISSessionFacade getSessionFacade() throws Exception {
        if (sessionFacade == null) {
            CMISSessionFacade sessionFacade = sessionFacadeFactory.create(getEndpoint());
            sessionFacade.initSession();
            // make sure to set sessionFacade to the field after successful initialisation
            // so that it has a valid session
            this.sessionFacade = sessionFacade;
        }

        return sessionFacade;
    }
}
