package com.armedia.explore.camelcmis;

import org.apache.camel.component.cmis.ArkCaseCMISSessionFacade;
import org.apache.camel.component.cmis.CMISEndpoint;
import org.apache.camel.component.cmis.CMISSessionFacade;
import org.apache.camel.component.cmis.DefaultCMISSessionFacadeFactory;
import org.apache.camel.util.EndpointHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ArkCaseSessionFacadeFactory extends DefaultCMISSessionFacadeFactory
{
    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public CMISSessionFacade create(CMISEndpoint endpoint) throws Exception
    {
        CMISSessionFacade facade = new ArkCaseCMISSessionFacade(endpoint.getCmsUrl());

        // must use a copy of the properties
        Map<String, Object> copy = new HashMap<>(endpoint.getProperties());
        // which we then set on the newly created facade
        EndpointHelper.setReferenceProperties(endpoint.getCamelContext(), facade, copy);
        EndpointHelper.setProperties(endpoint.getCamelContext(), facade, copy);

        return facade;

    }
}
