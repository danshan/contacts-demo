package com.shanhh.demo.contacts.web.resource;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dan.shan
 * @since 2014-10-22 11:17
 */
@Component
@NoArgsConstructor
public class ContactApplication extends Application implements InitializingBean {

    @Resource
    private ContactResource contactResource;

    /**
     * Singleton instances
     */
    private Set<Object> singletons = new HashSet<Object>();

    /**
     * Get Singletons
     *
     * @return Set of Objects
     */
    @Override
    public final Set<Object> getSingletons() {
        return singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        return set;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        singletons.add(contactResource);
    }
}
