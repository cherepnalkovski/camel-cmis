package org.apache.camel.component.cmis;

import org.apache.chemistry.opencmis.client.bindings.spi.StandardAuthenticationProvider;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ArkCaseCMISSessionFacade extends CMISSessionFacade
{
    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    private String remoteUser;

    private String arkcaseCmisUrl;

    public ArkCaseCMISSessionFacade(String cmsUrl)
    {
        super(cmsUrl);
        arkcaseCmisUrl = cmsUrl;
        logger.info("ArkCase CMIS Session Facade!");
    }

    @Override
    void initSession()
    {
        logger.info("In ArkCase CMIS facade");
        Map<String, String> parameter = new HashMap<>();
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.ATOMPUB_URL, getArkcaseCmisUrl());
        logger.info("Atompub URL: {}", parameter.get(SessionParameter.ATOMPUB_URL));
        parameter.put(SessionParameter.USER, "admin");
        parameter.put(SessionParameter.PASSWORD, "admin");
        parameter.put(SessionParameter.HEADER + ".0", "X-Alfresco-Remote-User:" + remoteUser);
        parameter.put(SessionParameter.AUTHENTICATION_PROVIDER_CLASS, StandardAuthenticationProvider.class.getName());
        parameter.put(SessionParameter.AUTH_HTTP_BASIC, "true");
        try
        {
            // "session" field is private in the superclass, with no setters, so we have to resort to reflection
            // to set it.
            FieldUtils.writeField(this, "session",
                    SessionFactoryLocator.getSessionFactory().getRepositories(parameter).get(0).createSession(), true);
            logger.info("Successfully set the session parameter!");
        }
        catch (IllegalAccessException e)
        {
            logger.error("Could not set session: {}", e.getMessage(), e);
        }

    }

    public String getRemoteUser()
    {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser)
    {
        this.remoteUser = remoteUser;
        logger.info("Remote user set to {}", remoteUser);
    }

    public String getArkcaseCmisUrl()
    {
        return arkcaseCmisUrl;
    }

    public void setArkcaseCmisUrl(String arkcaseCmisUrl)
    {
        this.arkcaseCmisUrl = arkcaseCmisUrl;
    }
}
