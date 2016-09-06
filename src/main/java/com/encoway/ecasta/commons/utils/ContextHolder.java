package com.encoway.ecasta.commons.utils;

import com.encoway.ecasta.TestsuiteConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Holder for spring {@link ApplicationContext}.
 * 
 * @author azzouz
 */
public class ContextHolder {

    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(TestsuiteConfiguration.class);

    public static AnnotationConfigApplicationContext getContext() {
        return (AnnotationConfigApplicationContext) CONTEXT;
    }

}
