package com.alioth4j.minispring.web;

import javax.servlet.http.HttpServletRequest;

public class WebDataBinderFactory {

    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName) {
        WebDataBinder wdb = new WebDataBinder(target, objectName);
        initBinder(wdb, request);
        return wdb;
    }

    protected void initBinder(WebDataBinder wdb, HttpServletRequest request) {
    }

}
