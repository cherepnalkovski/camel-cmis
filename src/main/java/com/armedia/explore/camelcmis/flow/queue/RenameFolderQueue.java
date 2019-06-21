package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class RenameFolderQueue {
    private ProducerTemplate producerTemplate;

    public RenameFolderQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:renameFolderQueue");
    }

    public Object renameFolder(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
