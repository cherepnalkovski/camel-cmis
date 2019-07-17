package org.apache.camel.component.cmis.exception;

public class CmisException extends RuntimeException {

    public CmisException() {
    }

    public CmisException(final String message) {
        super(message);
    }

    public CmisException(final Throwable cause) {
        super(cause);
    }

    public CmisException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CmisException(final String message, final Throwable cause,
                             final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
