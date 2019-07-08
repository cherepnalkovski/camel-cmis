package com.armedia.explore.camelcmis.flow.route;

import com.armedia.explore.camelcmis.Item;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cmis.CamelCMISActions;
import org.apache.camel.component.cmis.CamelCMISConstants;
import org.apache.chemistry.opencmis.commons.PropertyIds;

public class ReplaceFileRoute extends RouteBuilder
{
    @Override
    public void configure() {
        from("seda:replaceFileQueue").setExchangePattern(ExchangePattern.InOut)
                .to("seda:checkOutQueue")
                .to("seda:checkInQueue");
    }

}