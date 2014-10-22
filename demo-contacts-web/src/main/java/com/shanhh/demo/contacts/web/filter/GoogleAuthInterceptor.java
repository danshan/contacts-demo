package com.shanhh.demo.contacts.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.shanhh.demo.platform.commons.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class SecurityInterceptor.
 *
 * @author jack.zhang
 */
@Provider
@Component
public class GoogleAuthInterceptor implements ContainerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleAuthInterceptor.class);

    private static final String ACCESS_TOKEN = "access_token";

    @Value("${google.client.secret.path}")
    private String clientSecret;
    @Value("${google.oauth2.server}")
    private String oauth2Server;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        String accessToken = headers.getFirst(ACCESS_TOKEN);

        if ("/oauth2callback".equals(requestContext.getUriInfo().getPath())) {
            return;
        }
        if (StringUtils.isNotEmpty(accessToken)) {
            return;
        }

        requestContext.abortWith(buildRedirectResponse());
    }

    private Response buildRedirectResponse() {

        JSONObject secretJson = JSON.parseObject(FileUtils.getString(GoogleAuthInterceptor.class.getResource("/" + clientSecret).getPath()));

        List<String> scopes = new ArrayList<String>();
        scopes.add(oauth2Server);

        // Generate the URL to which we will direct users
        String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(
                secretJson.getJSONObject("web").getString("client_id"),
                secretJson.getJSONObject("web").getJSONArray("redirect_uris").getString(0),
                scopes).build();

        try {
            return Response.seeOther(new URI(authorizeUrl)).build();
        } catch (URISyntaxException e) {
            LOG.error("redirect uri incorrect, {}", authorizeUrl);
            return null;
        }
    }
}
