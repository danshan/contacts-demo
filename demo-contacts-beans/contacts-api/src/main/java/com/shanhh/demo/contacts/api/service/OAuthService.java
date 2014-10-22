package com.shanhh.demo.contacts.api.service;

/**
 * @author dan.shan
 * @since 2014-10-22 15:40
 */
public interface OAuthService {

    public String callback(String code);

    public String getAuthorizeUrl();

}
