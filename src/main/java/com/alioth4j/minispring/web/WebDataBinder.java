package com.alioth4j.minispring.web;

import com.alioth4j.minispring.beans.PropertyEditor;
import com.alioth4j.minispring.beans.PropertyValues;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WebDataBinder {

    private Object target;
    private Class<?> clz;
    private String objectName;
    AbstractPropertyAccessor propertyAccessor;

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    public void bind(HttpServletRequest request) {
        PropertyValues mpvs = assignParameters(request);
        addBindValues(mpvs, request);
        doBind(mpvs);
    }

    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request. "");
        return new PropertyValues(map);
    }

    protected void addBindValues(PropertyValues mpvs, HttpServletRequest request) {
    }

    private void doBind(PropertyValues mpvs) {
        getPropertyAccessor().setPropertyValues(mpvs);
    }

    protected AbstractPropertyAccessor getPropertyAccessor() {
        return this.propertyAccessor;
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

}
