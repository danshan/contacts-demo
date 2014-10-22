package com.shanhh.demo.contacts.web.resource.oauth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.contacts.ContactsService;
import com.shanhh.demo.contacts.api.service.OAuthService;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author dan.shan
 * @since 2014-10-22 14:21
 */
@Component
@Path("/oauth2callback")
public class OAuthCallbackResource {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthCallbackResource.class);

    @Resource
    private OAuthService oAuthService;

    @GET
    public Response callback(@Context HttpRequest request,
                             @QueryParam("code") String code) {

        oAuthService.callback(code);

        return Response.ok().cookie().build();
    }

}
