package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class RenameDocumentQueue {
    private ProducerTemplate producerTemplate;

    public RenameDocumentQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:renameDocumentQueue");
    }

    public Object renameDocument(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
