package com.shanhh.demo.contacts.web.resource;

import com.shanhh.demo.contacts.api.service.ContactService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author dan.shan
 * @since 2014-10-22 11:16
 */
@Component
@Path("/contacts")
public class ContactResource {
    @Resource
    ContactService contactService;

    @GET
    public Response findContact() {
        System.out.println(contactService.create());
        return Response.ok().build();
    }

}
