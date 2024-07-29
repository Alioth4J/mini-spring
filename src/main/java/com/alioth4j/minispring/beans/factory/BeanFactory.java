package com.alioth4j.minispring.beans.factory;

import com.alioth4j.minispring.beans.BeansException;

/**
 * Bean工厂
 * 注册Bean和获取Bean
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBean(String beanName, Object obj);

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

}