package com.armedia.explore.camelcmis;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

public class DeleteFolderQueue
{
    private ProducerTemplate producerTemplate;

    public Object deleteFolder(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
