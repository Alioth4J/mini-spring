package com.alioth4j.minispring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Map<String, MappingValue> mappingValues = new HashMap<>();
    private Map<String, Class<?>> mappingClz = new HashMap<>();
    private Map<String, Object> mappingObjs = new HashMap<>();

    private String sContextConfigLocation;

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Resource rs = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        this.mappingValues = reader.loadConfig(rs);
        refresh();
    }

    protected void refresh() {
        for (Map.Entry<String, MappingValue> entry : this.mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Object obj = null;
            try {
                obj = clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
               e.printStackTrace();
            }
            this.mappingClz.put(id, clz);
            this.mappingObjs.put(id, obj);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sPath = request.getServletPath();
        if (!this.mappingValues.containsKey(sPath)) {
            return;
        }
        Class<?> clz = this.mappingClz.get(sPath);
        Object obj = this.mappingObjs.get(sPath);
        String methodName = this.mappingValues.get(sPath).getMethod();
        Method method = clz.getMethod(methodName);
        Object objResult = null;
        try {
            objResult = method.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        response.getWriter().append(objResult)
    }

}
