package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class CreateDocumentQueue
{
    private ProducerTemplate producerTemplate;

    public CreateDocumentQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:createDocumentQueue");
    }

    public Object createDocument(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
