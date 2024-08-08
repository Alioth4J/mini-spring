package com.alioth4j.minispring.beans.factory.support;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.FactoryBean;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        Object object = doGetObjectFromFactoryBean(factoryBean, beanName);
        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return object;
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factoryBean, final String beanName) {
        Object object = null;
        try {
            object = factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
        return object;
    }

}
