package com.alioth4j.minispring.beans.factory.config;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.BeanFactory;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
    void setBeanFactory(BeanFactory beanFactory);

}
