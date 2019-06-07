package com.armedia.explore.camelcmis;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class Main {

    public static void main(String[] args) throws Exception {

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new CreateFolderRoute());
        camelContext.start();

        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CreateFolderQueue createFolderQueue = new CreateFolderQueue(producerTemplate);


        Item item = new Item();
        item.setFolderName("Vladimir");
        item.setPath("/User Homes/ann-acm");

        createFolderQueue.createFolder(item);

        System.out.println("FOLDER CREATED!!!!!");
    }
}
