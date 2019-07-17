package org.apache.camel.component.cmis.exception;

public class CamelCmisUnauthorizedException extends CamelCmisException {

    public CamelCmisUnauthorizedException() {
    }

    public CamelCmisUnauthorizedException(final String message) {
        super(message);
    }

    public CamelCmisUnauthorizedException(final Throwable cause) {
        super(cause);
    }

    public CamelCmisUnauthorizedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CamelCmisUnauthorizedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}