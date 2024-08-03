package com.alioth4j.minispring.web;

import com.alioth4j.minispring.context.ApplicationContext;

import javax.servlet.ServletContext;

public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();
    void setServletContext(ServletContext servletContext);

}
