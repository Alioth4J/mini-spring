package com.alioth4j.minispring.web.servlet;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.web.RequestMapping;
import com.alioth4j.minispring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

public class RequestMappingHandlerMapping implements HandlerMapping {

    WebApplicationContext wac;

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    protected void initMapping() {
        String[] controllerNames = this.wac.getBeanDefinitionNames();
        for (String controllerName : controllerNames) {
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Object obj = null;
            try {
                obj = this.wac.getBean(controllerName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            Method[] methods = clz.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping) {
                        String methodName = method.getName();
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                        this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                    }
                }
            }
        }
    }


    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String sPath = request.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(sPath)) {
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(sPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(sPath);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj);
        return handlerMethod;
    }

}
