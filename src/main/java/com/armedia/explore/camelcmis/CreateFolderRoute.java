package com.armedia.explore.camelcmis;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cmis.CamelCMISActions;
import org.apache.camel.component.cmis.CamelCMISConstants;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;

import java.util.UUID;

public class CreateFolderRoute extends RouteBuilder
{
    @Override
    public void configure() {
        from("seda:createFolderQueue").setExchangePattern(ExchangePattern.InOut)
                .process(exchange -> {
                    exchange.getIn().getHeaders().put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
                    exchange.getIn().getHeaders().put(PropertyIds.NAME, ((Item) exchange.getIn().getBody()).getFolderName() + UUID.randomUUID().toString());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_FOLDER_PATH, ((Item) exchange.getIn().getBody()).getPath());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_ACTION, CamelCMISActions.CREATE);
                })
               .to("arkcase-cmis://https://acm-arkcase/alfresco/api/-default-/public/cmis/versions/1.1/atom?username=admin&password=admin&remoteUser=ann-acm");
               // .to("arkcase-cmis://https://core.arkcase.dev.armedia.com/alfresco/api/-default-/public/cmis/versions/1.1/atom?remoteUser=ann-acm&username=admin&password=admin");
              //  .to("arkcase-cmis://https://core.arkcase.dev.armedia.com/alfresco/api/-default-/public/cmis/versions/1.1/atom?remoteUser=ann-acm");
    }

}

