package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class MoveDocumentQueue {
    private ProducerTemplate producerTemplate;

    public MoveDocumentQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:moveDocumentQueue");
    }

    public Object moveDocument(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
