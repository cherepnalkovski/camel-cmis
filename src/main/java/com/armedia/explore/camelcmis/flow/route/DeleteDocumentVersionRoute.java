package com.armedia.explore.camelcmis.flow.route;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cmis.CamelCMISActions;
import org.apache.camel.component.cmis.CamelCMISConstants;
import org.apache.chemistry.opencmis.commons.PropertyIds;

public class DeleteDocumentVersionRoute extends RouteBuilder
{
    @Override
    public void configure() throws Exception {
        from("seda:deleteDocumentVersionQueue").setExchangePattern(ExchangePattern.InOut)
                .process(exchange -> {
                    exchange.getIn().getHeaders().put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
                    exchange.getIn().getHeaders().put(PropertyIds.PATH, ((Item) exchange.getIn().getBody()).getPath());
                    exchange.getIn().getHeaders().put(PropertyIds.VERSION_LABEL, ((Item) exchange.getIn().getBody()).getVersion());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_ACTION, CamelCMISActions.DELETE_DOCUMENT_VERSION);
                })
                .to("arkcase-cmis://https://acm-arkcase/alfresco/api/-default-/public/cmis/versions/1.1/atom?username=admin&password=admin&remoteUser=ann-acm");
    }
}