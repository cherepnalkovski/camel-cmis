package com.armedia.explore.camelcmis.flow.route;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cmis.CamelCMISActions;
import org.apache.camel.component.cmis.CamelCMISConstants;
import org.apache.chemistry.opencmis.commons.PropertyIds;

public class MoveDocumentRoute extends RouteBuilder
{
    @Override
    public void configure() {
        from("seda:moveDocumentQueue").setExchangePattern(ExchangePattern.InOut)
                .process(exchange -> {
                    exchange.getIn().getHeaders().put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_OBJECT_ID, ((Item) exchange.getIn().getBody()).getObjectId());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_SOURCE_FOLDER_ID, ((Item) exchange.getIn().getBody()).getSourceFolderId());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_DESTIONATION_FOLDER_ID, ((Item) exchange.getIn().getBody()).getDestinationFolderId());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_ACTION, CamelCMISActions.MOVE_DOCUMENT);
                })
                .to("arkcase-cmis://https://acm-arkcase/alfresco/api/-default-/public/cmis/versions/1.1/atom?username=admin&password=admin&remoteUser=ann-acm");
    }

}