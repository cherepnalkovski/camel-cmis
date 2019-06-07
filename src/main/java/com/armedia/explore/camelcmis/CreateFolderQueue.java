package com.armedia.explore.camelcmis;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

public class CreateFolderQueue
{
    private ProducerTemplate producerTemplate;

    public CreateFolderQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:createFolderQueue");
    }

    public Object createFolder(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
