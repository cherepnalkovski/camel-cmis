package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class MoveFolderQueue {
    private ProducerTemplate producerTemplate;

    public MoveFolderQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:moveFolderQueue");
    }

    public Object moveFolder(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
