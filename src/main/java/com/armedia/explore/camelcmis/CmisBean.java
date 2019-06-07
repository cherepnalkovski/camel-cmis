package com.armedia.explore.camelcmis;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.TypeConverter;
import org.apache.camel.spi.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmisBean
{
    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    public void cmisObjectInfo(
            Exchange exchange,
            Message message,
            CamelContext camelContext,
            Registry registry,
            TypeConverter typeConverter)
    {
        logger.info("Got something from CMIS: " +
                "Exchange {}, " +
                "Message {}, " +
                "Context {}, " +
                "Registry {}, " +
                "Type converter {}",
                (exchange != null),
                (message != null),
                (camelContext != null),
                (registry != null),
                (typeConverter != null));

        logger.info("{} headers", message.getHeaders().size());
        logger.info("Path: {}", message.getHeader("cmis:path"));
        // message.getHeaders().entrySet().forEach(e -> logger.info("{} = {}", e.getKey(), e.getValue()));

    }
}
