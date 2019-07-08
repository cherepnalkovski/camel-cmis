package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class CheckInQueue {
    private ProducerTemplate producerTemplate;

    public CheckInQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:checkInQueue");
    }

    public Object checkIn(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
