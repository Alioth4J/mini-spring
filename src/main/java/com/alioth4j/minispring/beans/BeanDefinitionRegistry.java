package com.alioth4j.minispring.beans;

/**
 * 集中存放、管理BeanDefinition的容器
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition bd);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);

}
