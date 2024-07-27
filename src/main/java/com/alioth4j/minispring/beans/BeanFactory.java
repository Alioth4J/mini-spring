package com.alioth4j.minispring.beans;

/**
 * Bean工厂
 * 注册Bean和获取Bean
 */
public interface BeanFactory {

    void registerBeanDefinition(BeanDefinition beanDefinition);

    Object getBean(String beanName) throws BeansException;

}
