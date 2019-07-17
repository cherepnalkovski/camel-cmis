package com.armedia.explore.camelcmis;

import com.armedia.explore.camelcmis.flow.queue.*;
import com.armedia.explore.camelcmis.flow.route.*;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws Exception {

        CamelContext context = init();

    //    createDocument(context);

        createFolder("Vladimir1", context);

    //    createFolder("Test", context);

    //    deleteDocument(context);

    //    deleteFolder(context);

     //   copyDocument(context);

    //    copyFolder(context); // tested with sub folders and files

   //     moveDocument(context);

    //    moveFolder(context);  // create copy than delete

    //    renameDocument(context);  // Full name with extension

    //    renameFolder(context);

    //    cancelCheckOut(context);

     //   replaceFileRoute(context);

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
        camelContext.addRoutes(new CancelCheckoutRoute());
        camelContext.addRoutes(new CreateDocumentRoute());
        camelContext.addRoutes(new CheckInRoute());
        camelContext.addRoutes(new CheckOutRoute());
        camelContext.addRoutes(new ReplaceFileRoute());

        camelContext.start();

        return camelContext;
    }

    public static void createFolder(String name, CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CreateFolderQueue createFolderQueue = new CreateFolderQueue(producerTemplate);
        Item item = new Item();
        item.setObjectId("14576e13-d2fc-42ec-97ba-a2940de953f7");
        item.setName(name);
        createFolderQueue.createFolder(item);
        System.out.println("FOLDER CREATED!!!!!");
    }

    public static void createDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CreateDocumentQueue createDocumentQueue = new CreateDocumentQueue(producerTemplate);

        File initialFile = new File("src/main/resources/application.properties");
        InputStream inputStream = new FileInputStream(initialFile);

        Item item = new Item();
        item.setName(initialFile.getName());
        item.setParentFolderPath("/User Homes/ann-acm");
        item.setInputStream(inputStream);
        item.setMimeType("text/plain");

        createDocumentQueue.createDocument(item);
    }

    public static void deleteFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        DeleteFolderQueue deleteFolderQueue = new DeleteFolderQueue(producerTemplate);

        Item deleteFolder = new Item();
        deleteFolder.setObjectId("357462a6-87c7-4ea4-bb20-d5b2f4a64ad0");

        deleteFolderQueue.deleteFolder(deleteFolder);
        System.out.println("FOLDER DELETED!!!!!");
    }

    public static void deleteDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        DeleteDocumentQueue deleteDocumentQueue = new DeleteDocumentQueue(producerTemplate);
        Item document = new Item();
        document.setObjectId("5587c258-6afe-4a09-81d2-cf4fd2573cf8");

        deleteDocumentQueue.deleteDocument(document);
        System.out.println("DOCUMENT DELETED!!!!!");
    }

    public static void copyDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CopyDocumentQueue copyDocumentQueue = new CopyDocumentQueue(producerTemplate);
        Item copyItem = new Item();
        copyItem.setObjectId("49c9dbcd-4b60-4f7b-bb32-aa8ed4ea7304");
        copyItem.setDestinationFolderId("06d42c97-5f0e-4661-a68f-b7e47d64bb90");

        copyDocumentQueue.copyDocument(copyItem);

    }

    public static void copyFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CopyFolderQueue copyFolderQueue = new CopyFolderQueue(producerTemplate);
        Item item = new Item();
        item.setObjectId("14576e13-d2fc-42ec-97ba-a2940de953f7");
        item.setDestinationFolderId("ec7d75f5-0c89-4f8b-9b39-d5446e36c4d2");

        copyFolderQueue.copyFolder(item);
    }

    public static void moveDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        MoveDocumentQueue moveDocumentQueue = new MoveDocumentQueue(producerTemplate);
        Item item = new Item();
        item.setObjectId("49c9dbcd-4b60-4f7b-bb32-aa8ed4ea7304");
        item.setSourceFolderId("0b3fd943-0411-49b9-be40-f6c3689dd083");
        item.setDestinationFolderId("ec7d75f5-0c89-4f8b-9b39-d5446e36c4d2");

        moveDocumentQueue.moveDocument(item);
    }

    public static void moveFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        MoveFolderQueue moveFolderQueue = new MoveFolderQueue(producerTemplate);
        Item item = new Item();
        item.setObjectId("14576e13-d2fc-42ec-97ba-a2940de953f7");
        item.setDestinationFolderId("ec7d75f5-0c89-4f8b-9b39-d5446e36c4d2");

        moveFolderQueue.moveFolder(item);
    }

    public static void renameDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        RenameDocumentQueue renameDocumentQueue = new RenameDocumentQueue(producerTemplate);
        Item item = new Item();
        item.setObjectId("49c9dbcd-4b60-4f7b-bb32-aa8ed4ea7304");
        item.setName("LastRename");

        renameDocumentQueue.renameDocument(item);
    }

    public static void renameFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        RenameFolderQueue renameFolderQueue = new RenameFolderQueue(producerTemplate);
        Item item = new Item();
        item.setObjectId("14576e13-d2fc-42ec-97ba-a2940de953f7");
        item.setName("LastFolderRenamed");

        renameFolderQueue.renameFolder(item);
    }

    private static void cancelCheckOut(CamelContext camelContext) throws Exception
    {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CancelCheckoutQueue cancelCheckoutQueue = new CancelCheckoutQueue(producerTemplate);

        Item item = new Item();
        item.setObjectId("");

        cancelCheckoutQueue.cancelCheckout(item);
    }

    public static void replaceFileRoute(CamelContext camelContext) throws Exception
    {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        ReplaceFileQueue replaceFileQueue = new ReplaceFileQueue(producerTemplate);

        File file = new File("src/main/resources/application.properties");
        InputStream inputStream = new FileInputStream(file);

        Item item = new Item();
        item.setObjectId("49c9dbcd-4b60-4f7b-bb32-aa8ed4ea7304");
        item.setName("REPLACED");
        item.setMimeType("text/plain");
        item.setInputStream(inputStream);
        item.setCheckInComment("Replaced file");

        replaceFileQueue.replaceFile(item);
    }
}
