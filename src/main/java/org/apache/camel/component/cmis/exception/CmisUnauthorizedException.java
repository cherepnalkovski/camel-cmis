package org.apache.camel.component.cmis.exception;

public class CmisUnauthorizedException extends CmisException {

    public CmisUnauthorizedException() {
    }

    public CmisUnauthorizedException(final String message) {
        super(message);
    }

    public CmisUnauthorizedException(final Throwable cause) {
        super(cause);
    }

    public CmisUnauthorizedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CmisUnauthorizedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}