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
/*
        createFolder("Vladimir", context);

        createFolder("Test", context);*/

    //    deleteDocument(context);

    //    deleteFolder(context);

    //    copyDocument(context);

     //   copyFolder(context); // tested with sub folders and files

    //    moveDocument(context);

    //   moveFolder(context);  // create copy than delete

    //    renameDocument(context);  // Full name with extension

    //    renameFolder(context);

        // replace(context) - uploads new version of the file ?

    }

    public static CamelContext init() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new CreateFolderRoute());
        camelContext.addRoutes(new DeleteFolderRoute());
        camelContext.addRoutes(new DeleteDocumentRoute());
        camelContext.addRoutes(new CopyDocumentRoute());
        camelContext.addRoutes(new CopyFolderRoute());
        camelContext.addRoutes(new MoveDocumentRoute());
        camelContext.addRoutes(new MoveFolderRoute());
        camelContext.addRoutes(new RenameDocumentRoute());
        camelContext.addRoutes(new RenameFolderRoute());
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

    public static void copyDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CopyDocumentQueue copyDocumentQueue = new CopyDocumentQueue(producerTemplate);
        Item copyItem = new Item();
        copyItem.setDestinationPath("/User Homes/ann-acm/TestRenamed");
        copyItem.setDocumentPath("/User Homes/ann-acm/Vladimir/Test.xlsx");

        copyDocumentQueue.copyDocument(copyItem);

    }

    public static void copyFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CopyFolderQueue copyFolderQueue = new CopyFolderQueue(producerTemplate);
        Item item = new Item();
        item.setDestinationPath("/User Homes/ann-acm/Move");
        item.setFolderPath("/User Homes/ann-acm/Vladimir");

        copyFolderQueue.copyFolder(item);
    }

    public static void moveDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        MoveDocumentQueue moveDocumentQueue = new MoveDocumentQueue(producerTemplate);
        Item item = new Item();
        item.setDestinationPath("/User Homes/ann-acm/Vladimir");
        item.setFolderPath("/User Homes/ann-acm/");
        item.setDocumentPath("/User Homes/ann-acm/TestRenamed.xlsx");

        moveDocumentQueue.moveDocument(item);
    }

    public static void moveFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        MoveFolderQueue moveFolderQueue = new MoveFolderQueue(producerTemplate);
        Item item = new Item();
        item.setDestinationPath("/User Homes/ann-acm/Remove");
        item.setFolderPath("/User Homes/ann-acm/Move");

        moveFolderQueue.moveFolder(item);
    }

    public static void renameDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        RenameDocumentQueue renameDocumentQueue = new RenameDocumentQueue(producerTemplate);
        Item item = new Item();
        item.setFileName("TestAgain");
        item.setDocumentPath("/User Homes/ann-acm/TestRenamed1.xlsx");

        renameDocumentQueue.renameDocument(item);
    }

    public static void renameFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        RenameFolderQueue renameFolderQueue = new RenameFolderQueue(producerTemplate);
        Item item = new Item();
        item.setFolderName("TestRenamed");
        item.setFolderPath("/User Homes/ann-acm/Test");

        renameFolderQueue.renameFolder(item);
    }
}
