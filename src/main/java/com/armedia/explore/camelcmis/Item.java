package com.armedia.explore.camelcmis;


import java.io.InputStream;

public class Item
{
    private String objectId;
    private String destinationFolderId;
    private String sourceFolderId;
    private String name;
    private String parentFolderPath;
    private String version;
    private String mimeType;
    private String checkInComment;
    private InputStream inputStream;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDestinationFolderId() {
        return destinationFolderId;
    }

    public void setDestinationFolderId(String destinationFolderId) {
        this.destinationFolderId = destinationFolderId;
    }

    public String getSourceFolderId() {
        return sourceFolderId;
    }

    public void setSourceFolderId(String sourceFolderId) {
        this.sourceFolderId = sourceFolderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentFolderPath() {
        return parentFolderPath;
    }

    public void setParentFolderPath(String parentFolderPath) {
        this.parentFolderPath = parentFolderPath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getCheckInComment() {
        return checkInComment;
    }

    public void setCheckInComment(String checkInComment) {
        this.checkInComment = checkInComment;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
