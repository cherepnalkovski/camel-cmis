package com.armedia.explore.camelcmis.flow.route;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cmis.CamelCMISActions;
import org.apache.camel.component.cmis.CamelCMISConstants;
import org.apache.chemistry.opencmis.commons.PropertyIds;

public class RenameFolderRoute extends RouteBuilder
{
    @Override
    public void configure() {
        from("seda:renameFolderQueue").setExchangePattern(ExchangePattern.InOut)
                .process(exchange -> {
                    exchange.getIn().getHeaders().put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_OBJECT_ID, ((Item) exchange.getIn().getBody()).getObjectId());
                    exchange.getIn().getHeaders().put(PropertyIds.NAME, ((Item) exchange.getIn().getBody()).getName());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_ACTION, CamelCMISActions.RENAME_FOLDER);
                })
                .to("arkcase-cmis://https://acm-arkcase/alfresco/api/-default-/public/cmis/versions/1.1/atom?username=admin&password=admin&remoteUser=ann-acm");
    }

}