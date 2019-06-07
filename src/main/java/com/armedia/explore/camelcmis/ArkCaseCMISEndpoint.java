package com.armedia.explore.camelcmis;

import org.apache.camel.component.cmis.CMISComponent;
import org.apache.camel.component.cmis.CMISEndpoint;
import org.apache.camel.component.cmis.CMISSessionFacadeFactory;

public class ArkCaseCMISEndpoint extends CMISEndpoint
{
    private String remoteUser;

    public ArkCaseCMISEndpoint(String uri, CMISComponent component, String cmsUrl)
    {
        super(uri, component, cmsUrl);
    }

    @Override
    public String getCmsUrl()
    {
        String url = super.getCmsUrl();
        url += "?X-Alfresco-Remote-User=" + getRemoteUser();
        return url;
    }


    public ArkCaseCMISEndpoint(String uri, CMISComponent component, String cmsUrl, CMISSessionFacadeFactory sessionFacadeFactory)
    {
        super(uri, component, cmsUrl, sessionFacadeFactory);
    }

    public String getRemoteUser()
    {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser)
    {
        this.remoteUser = remoteUser;
    }
}
