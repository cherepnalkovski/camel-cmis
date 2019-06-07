package com.armedia.explore.camelcmis;

import com.armedia.explore.camelcmis.flow.queue.CreateFolderQueue;
import com.armedia.explore.camelcmis.flow.queue.DeleteDocumentQueue;
import com.armedia.explore.camelcmis.flow.queue.DeleteDocumentVersionQueue;
import com.armedia.explore.camelcmis.flow.route.CreateFolderRoute;
import com.armedia.explore.camelcmis.flow.queue.DeleteFolderQueue;
import com.armedia.explore.camelcmis.flow.route.DeleteDocumentRoute;
import com.armedia.explore.camelcmis.flow.route.DeleteDocumentVersionRoute;
import com.armedia.explore.camelcmis.flow.route.DeleteFolderRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class Main {

    public static void main(String[] args) throws Exception {

        CamelContext context = init();

    //    createFolder("Vladimir test", context);

    //    deleteDocumentVersion(context);

        deleteDocument(context);

    }

    public static CamelContext init() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new CreateFolderRoute());
        camelContext.addRoutes(new DeleteFolderRoute());
        camelContext.addRoutes(new DeleteDocumentRoute());
        camelContext.addRoutes(new DeleteDocumentVersionRoute());
        camelContext.start();

        return camelContext;

    }

    public static void createFolder(String name, CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        CreateFolderQueue createFolderQueue = new CreateFolderQueue(producerTemplate);
        Item item = new Item();
        item.setFolderName(name);
        item.setPath("/User Homes/ann-acm");
        createFolderQueue.createFolder(item);
        System.out.println("FOLDER CREATED!!!!!");
    }

    public static void deleteFolder(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        DeleteFolderQueue deleteFolderQueue = new DeleteFolderQueue(producerTemplate);

        Item deleteFolder = new Item();
        deleteFolder.setPath("/User Homes/ann-acm/Vladimirfd1ecf69-4a51-46d0-be21-988086124b82");

        deleteFolderQueue.deleteFolder(deleteFolder);
        System.out.println("FOLDER DELETED!!!!!");
    }

    public static void deleteDocument(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        DeleteDocumentQueue deleteDocumentQueue = new DeleteDocumentQueue(producerTemplate);
        Item document = new Item();
        document.setPath("/User Homes/ann-acm/Documents/Test.xlsx");

        deleteDocumentQueue.deleteDocument(document);
        System.out.println("DOCUMENT DELETED!!!!!");
    }

    // Can delete only the last version. Problem with deleting version < last
    public static void deleteDocumentVersion(CamelContext camelContext) throws Exception {
        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();

        DeleteDocumentVersionQueue deleteDocumentVersionQueue = new DeleteDocumentVersionQueue(producerTemplate);
        Item documentVersion = new Item();
        documentVersion.setPath("/User Homes/ann-acm/Documents/Test.xlsx");
        documentVersion.setVersion("3.0");

        deleteDocumentVersionQueue.deleteDocumentVersion(documentVersion);
    }
}
