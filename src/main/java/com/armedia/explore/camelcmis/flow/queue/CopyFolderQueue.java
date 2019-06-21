package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class CopyFolderQueue {
    private ProducerTemplate producerTemplate;

    public CopyFolderQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:copyFolderQueue");
    }

    public Object copyFolder(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
