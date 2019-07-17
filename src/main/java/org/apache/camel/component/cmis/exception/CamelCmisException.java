package org.apache.camel.component.cmis.exception;

public class CamelCmisException extends RuntimeException {

    public CamelCmisException() {
    }

    public CamelCmisException(final String message) {
        super(message);
    }

    public CamelCmisException(final Throwable cause) {
        super(cause);
    }

    public CamelCmisException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CamelCmisException(final String message, final Throwable cause,
                             final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
