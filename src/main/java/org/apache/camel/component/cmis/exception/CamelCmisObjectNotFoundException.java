package org.apache.camel.component.cmis.exception;

public class CamelCmisObjectNotFoundException extends CamelCmisException {

    public CamelCmisObjectNotFoundException() {
    }

    public CamelCmisObjectNotFoundException(final String message) {
        super(message);
    }

    public CamelCmisObjectNotFoundException(final Throwable cause) {
        super(cause);
    }

    public CamelCmisObjectNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CamelCmisObjectNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}