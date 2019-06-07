package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class DeleteDocumentQueue
{
    private ProducerTemplate producerTemplate;

    public DeleteDocumentQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:deleteDocumentQueue");
    }

    public Object deleteDocument(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
