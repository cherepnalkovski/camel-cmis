package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class ReplaceFileQueue {
    private ProducerTemplate producerTemplate;

    public ReplaceFileQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:replaceFileQueue");
    }

    public Object replaceFile(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
