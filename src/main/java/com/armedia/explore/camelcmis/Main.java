package com.armedia.explore.camelcmis;

import com.armedia.explore.camelcmis.flow.queue.*;
import com.armedia.explore.camelcmis.flow.route.*;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class Main {

    public static void main(String[] args) throws Exception {

        CamelContext context = init();

    //    createFolder("VladimirReflection", context);

    //    deleteDocument(context);

    //    copy(context);

        deleteFolder(context);

    }

    public static CamelContext init() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new CreateFolderRoute());
        camelContext.addRoutes(new DeleteFolderRoute());
        camelContext.addRoutes(new DeleteDocumentRoute());
        camelContext.addRoutes(new DeleteDocumentVersionRoute());
        camelContext.addRoutes(new CopyDocumentRoute());
        camelContext.start();

        return camelContext;

    }

    public static void createFolder(String name, CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CreateFolderQueue createFolderQueue = new CreateFolderQueue(producerTemplate);
        Item item = new Item();
        item.setFolderName(name);
        item.setFolderPath("/User Homes/ann-acm");
        createFolderQueue.createFolder(item);
        System.out.println("FOLDER CREATED!!!!!");
    }

    public static void deleteFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        DeleteFolderQueue deleteFolderQueue = new DeleteFolderQueue(producerTemplate);

        Item deleteFolder = new Item();
        deleteFolder.setFolderPath("/User Homes/ann-acm/VladimirReflection");

        deleteFolderQueue.deleteFolder(deleteFolder);
        System.out.println("FOLDER DELETED!!!!!");
    }

    public static void deleteDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        DeleteDocumentQueue deleteDocumentQueue = new DeleteDocumentQueue(producerTemplate);
        Item document = new Item();
        document.setFolderPath("/User Homes/ann-acm/Documents/Test.xlsx");

        deleteDocumentQueue.deleteDocument(document);
        System.out.println("DOCUMENT DELETED!!!!!");
    }

    public static void copy(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CopyDocumentQueue copyDocumentQueue = new CopyDocumentQueue(producerTemplate);
        Item copyItem = new Item();
        copyItem.setDestinationPath("/User Homes/ann-acm/VladimirCopy");
        copyItem.setDocumentPath("/User Homes/ann-acm/Vladimir/Test.xlsx");

        copyDocumentQueue.copyDocument(copyItem);

    }
}
