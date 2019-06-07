package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class DeleteDocumentVersionQueue
{
    private ProducerTemplate producerTemplate;

    public DeleteDocumentVersionQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:deleteDocumentVersionQueue?timeout=1000000");
    }

    public Object deleteDocumentVersion(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
