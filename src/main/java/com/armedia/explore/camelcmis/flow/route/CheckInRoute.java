package com.armedia.explore.camelcmis.flow.route;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cmis.CamelCMISActions;
import org.apache.camel.component.cmis.CamelCMISConstants;
import org.apache.chemistry.opencmis.commons.PropertyIds;

public class CheckInRoute extends RouteBuilder
{
    @Override
    public void configure() {
        from("seda:checkInQueue").setExchangePattern(ExchangePattern.InOut)
                .process(exchange -> {
                    exchange.getIn().getHeaders().put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
                    exchange.getIn().getHeaders().put(PropertyIds.NAME, ((Item) exchange.getIn().getBody()).getName());
                    exchange.getIn().getHeaders().put(PropertyIds.CONTENT_STREAM_MIME_TYPE, ((Item) exchange.getIn().getBody()).getMimeType());
                    exchange.getIn().getHeaders().put(PropertyIds.CHECKIN_COMMENT,((Item) exchange.getIn().getBody()).getCheckInComment());
                    exchange.getIn().getHeaders().put(CamelCMISConstants.CMIS_ACTION, CamelCMISActions.CHECK_IN);

                    Item item = (Item) exchange.getIn().getBody();
                    exchange.getIn().setBody(item.getInputStream());
                })
                .to("arkcase-cmis://https://acm-arkcase/alfresco/api/-default-/public/cmis/versions/1.1/atom?username=admin&password=admin&remoteUser=ann-acm");
    }

}