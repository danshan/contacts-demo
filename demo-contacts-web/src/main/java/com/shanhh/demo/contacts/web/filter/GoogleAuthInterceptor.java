package com.shanhh.demo.contacts.web.filter;

import com.shanhh.demo.contacts.api.service.OAuthService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The Class SecurityInterceptor.
 *
 * @author jack.zhang
 */
@Provider
@Component
public class GoogleAuthInterceptor implements ContainerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleAuthInterceptor.class);

    @Resource
    private OAuthService oAuthService;

    private static final String ACCESS_TOKEN = "access_token";

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
        String authorizeUrl = oAuthService.getAuthorizeUrl();
        try {
            return Response.seeOther(new URI(authorizeUrl)).build();
        } catch (URISyntaxException e) {
            LOG.error("redirect uri incorrect, {}", authorizeUrl);
            return null;
        }
    }
}
