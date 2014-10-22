package com.shanhh.demo.contacts.biz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.shanhh.demo.contacts.api.service.OAuthService;
import com.shanhh.demo.platform.commons.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author dan.shan
 * @since 2014-10-22 15:44
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthServiceImpl.class);

    @Value("${google.client.secret.path}")
    private String clientSecret;
    @Value("${google.oauth2.server}")
    private String oauth2Server;

    @Override
    public String callback(String code) {
        ContactsService contactsService = new ContactsService("demo-contacts");

        Credential credential = exchangeCode(code);
        contactsService.setOAuth2Credentials(credential);
        contactsService.useSsl();
        contactsService.setHeader("GData-Version", "3.0");

        String key = UUID.randomUUID().toString();
        return key;
    }

    @Override
    public String getAuthorizeUrl() {
        JSONObject secretJson = JSON.parseObject(FileUtils.getString(OAuthServiceImpl.class.getResource("/" + clientSecret).getPath()));

        List<String> scopes = new ArrayList<String>();
        scopes.add(oauth2Server);

        // Generate the URL to which we will direct users
        String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(
                secretJson.getJSONObject("web").getString("client_id"),
                secretJson.getJSONObject("web").getJSONArray("redirect_uris").getString(0),
                scopes).build();

        return authorizeUrl;
    }


    /**
     * Exchange an authorization code for OAuth 2.0 credentials.
     *
     * @param authorizationCode Authorization code to exchange for OAuth 2.0
     *        credentials.
     * @return OAuth 2.0 credentials.
     */
    private Credential exchangeCode(String authorizationCode) {
        try {
            GoogleAuthorizationCodeFlow flow = getFlow();
            GoogleTokenResponse response =
                    flow.newTokenRequest(authorizationCode).setRedirectUri(getRedirectUri()).execute();
            return flow.createAndStoreCredential(response, null);
        } catch (IOException e) {
            LOG.error("exchange code failed: ", e);
            return null;
        }
    }

    private String getRedirectUri() {
        JSONObject secretJson = JSON.parseObject(FileUtils.getString(OAuthServiceImpl.class.getResource("/" + clientSecret).getPath()));
        return secretJson.getJSONObject("web").getJSONArray("redirect_uris").getString(0);
    }

    /**
     * Build an authorization flow and store it as a static class attribute.
     *
     * @return GoogleAuthorizationCodeFlow instance.
     * @throws IOException
     *         Unable to load client_secrets.json.
     */
    private GoogleAuthorizationCodeFlow getFlow() throws IOException {
        List<String> scopes = new ArrayList<String>();
        scopes.add(oauth2Server);

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory, new FileReader(new File(OAuthServiceImpl.class.getResource("/" + clientSecret).getPath())));
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, scopes)
                        .setAccessType("online").setApprovalPrompt("force").build();
        return flow;
    }

}
