package com.alioth4j.minispring.aop;

import com.alioth4j.minispring.beans.factory.FactoryBean;
import com.alioth4j.minispring.util.ClassUtils;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private AopProxyFactory aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private Object target;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private Object singletonInstance;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    @Override
    public Object getObject() throws Exception {
        return getSingletonInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target);
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public void setInterceptorNames(String[] interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    private synchronized Object getSingletonInstance() {
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    protected Object getProxy(AopProxy aopProxy) {
        return aopProxy.getProxy();
    }

}
