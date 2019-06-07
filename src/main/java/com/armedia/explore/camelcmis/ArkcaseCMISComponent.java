package com.armedia.explore.camelcmis;

import org.apache.camel.Endpoint;
import org.apache.camel.component.cmis.CMISComponent;
import org.apache.camel.component.cmis.CMISEndpoint;
import org.apache.camel.component.cmis.CMISSessionFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ArkcaseCMISComponent extends CMISComponent
{
    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    public ArkcaseCMISComponent()
    {
        setSessionFacadeFactory(new ArkCaseSessionFacadeFactory());
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception
    {
        logger.info("Using the ArkCase endpoint!!! uri: {}, remaining: {}, parameter count: {}", uri, remaining, parameters.size());
        parameters.entrySet().forEach(e -> logger.info("{} = {}", e.getKey(), e.getValue()));

        CMISEndpoint endpoint = new ArkCaseCMISEndpoint(uri, this, remaining);

        // create a copy of parameters which we need to store on the endpoint which are in use from the session factory
        Map<String, Object> copy = new HashMap<>(parameters);
        endpoint.setProperties(copy);
        if (getSessionFacadeFactory() != null)
        {
            endpoint.setSessionFacadeFactory(getSessionFacadeFactory());
        }

        // create a dummy CMISSessionFacade which we set the properties on
        // so we can validate if they are all known options and fail fast if there are unknown options
        CMISSessionFacade dummy = new CMISSessionFacade(remaining);
        setProperties(dummy, parameters);

        // and the remainder options are for the endpoint
        setProperties(endpoint, parameters);

        return endpoint;
    }
}
