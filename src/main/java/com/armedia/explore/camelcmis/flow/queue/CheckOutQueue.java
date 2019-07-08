package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class CheckOutQueue {
    private ProducerTemplate producerTemplate;

    public CheckOutQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:checkOutQueue");
    }

    public Object checkIn(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
