package com.alioth4j.minispring.web.servlet;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {

    HandlerMethod getHandler(HttpServletRequest request) throws Exception;

}
