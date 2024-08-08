package com.alioth4j.minispring.aop.framework.autoproxy;

import com.alioth4j.minispring.aop.AopProxyFactory;
import com.alioth4j.minispring.aop.DefaultAopProxyFactory;
import com.alioth4j.minispring.aop.PointcutAdvisor;
import com.alioth4j.minispring.aop.ProxyFactoryBean;
import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.BeanFactory;
import com.alioth4j.minispring.beans.factory.config.BeanPostProcessor;
import com.alioth4j.minispring.util.PatternMatchUtils;

public class BeanNameAutoProxyCreator implements BeanPostProcessor {

    String pattern;
    private BeanFactory beanFactory;
    private AopProxyFactory aopProxyFactory;
    private String interceptorName;
    private PointcutAdvisor advisor;

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (isMatch(beanName, this.pattern)) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(bean);
            proxyFactoryBean.setBeanFactory(this.beanFactory);
            proxyFactoryBean.setAopProxyFactory(this.aopProxyFactory);
            proxyFactoryBean.setInterceptorName(this.interceptorName);
        }
    }

    private boolean isMatch(String beanName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
