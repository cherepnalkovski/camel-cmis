package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

public class DeleteFolderQueue
{
    private ProducerTemplate producerTemplate;

    public DeleteFolderQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:deleteFolderQueue");
    }

    public Object deleteFolder(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
