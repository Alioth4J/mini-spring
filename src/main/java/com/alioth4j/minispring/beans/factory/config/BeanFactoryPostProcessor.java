package com.alioth4j.minispring.beans.factory.config;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;

}
