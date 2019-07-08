package com.armedia.explore.camelcmis.flow.queue;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ProducerTemplate;

public class CancelCheckoutQueue {

    private ProducerTemplate producerTemplate;

    public CancelCheckoutQueue(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
        producerTemplate.setDefaultEndpointUri("seda:cancelCheckOutQueue");
    }

    public Object cancelCheckout(Item item)
    {
        return (Object) producerTemplate.requestBody(item);
    }
}
