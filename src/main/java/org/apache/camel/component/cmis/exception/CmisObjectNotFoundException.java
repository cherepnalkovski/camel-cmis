package org.apache.camel.component.cmis.exception;

public class CmisObjectNotFoundException extends CmisException {

    public CmisObjectNotFoundException() {
    }

    public CmisObjectNotFoundException(final String message) {
        super(message);
    }

    public CmisObjectNotFoundException(final Throwable cause) {
        super(cause);
    }

    public CmisObjectNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CmisObjectNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}