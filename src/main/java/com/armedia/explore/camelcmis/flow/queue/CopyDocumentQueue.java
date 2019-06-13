package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class CopyDocumentQueue {
    private ProducerTemplate producerTemplate;

    public CopyDocumentQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:copyDocumentQueue");
    }

    public Object copyDocument(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
