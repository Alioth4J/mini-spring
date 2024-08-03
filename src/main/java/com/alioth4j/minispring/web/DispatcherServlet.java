package com.alioth4j.minispring.web;

import com.alioth4j.minispring.web.servlet.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    private String sContextConfigLocation;

    private WebApplicationContext webApplicationContext;
    private WebApplicationContext parentApplicationContext;

    private List<String> packageNames = new ArrayList<>();

    private List<String> controllerNames = new ArrayList<>();
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    private Map<String, Object> controllerObjs = new HashMap<>();

    private HandlerMapping handlerMapping;
`   private HandlerAdapter handlerAdapter;

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);
        refresh();
    }

    protected void refresh() {
        initController();
        initHandlerMappings(this.webApplicationContext);
        initMappingAdapter(this.webApplicationContext);
    }

    private void initHandlerMappings(WebApplicationContext webApplicationContext) {
        this.handlerMapping = new RequestMappingHandlerMapping(webApplicationContext);
    }

    private void initMappingAdapter(WebApplicationContext webApplicationContext) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(webApplicationContext);
    }

    protected void initController() {
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            this.controllerClasses.put(controllerName, clz);
            Object obj = null;
            try {
                obj = clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            this.controllerObjs.put(controllerName, obj);
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packageNames){
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpServletRequest processedRequest = req;
        HandlerMethod handlerMethod = null;
        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(req, resp, handlerMethod);
    }

}
