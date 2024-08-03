package com.alioth4j.minispring.web;

import com.alioth4j.minispring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }


    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
