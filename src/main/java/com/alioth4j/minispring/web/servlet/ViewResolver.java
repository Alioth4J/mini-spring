package com.alioth4j.minispring.web.servlet;

public interface ViewResolver {

    View resolveViewName(String viewName) throws Exception;

}
