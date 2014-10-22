package com.shanhh.demo.contacts.biz.domain;

import lombok.Data;

/**
 * @author dan.shan
 * @since 2014-10-21 17:06
 */
@Data
public class Contact {

    private String firstName;
    private String lastName;

    public int create() {
        return 0;
    }

}
