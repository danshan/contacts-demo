package com.shanhh.demo.contacts.biz.service;

import com.shanhh.demo.contacts.api.service.ContactService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

/**
 * @author dan.shan
 * @since 2014-10-22 11:36
 */
@Service
public class ContactServiceImpl implements ContactService {
    @Override
    public int create() {
        return RandomUtils.nextInt(1000, 9999);
    }
}
