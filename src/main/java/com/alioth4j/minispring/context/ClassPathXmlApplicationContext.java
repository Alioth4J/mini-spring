package com.alioth4j.minispring.context;

import com.alioth4j.minispring.beans.*;
import com.alioth4j.minispring.core.ClassPathXmlResource;
import com.alioth4j.minispring.core.Resource;

/**
 * 作为外部集成包装
 * 加载资源（读取、解析XML文件），构建BeanDefinition，注入到BeanFactory中
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        // TODO
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        // TODO
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        // TODO
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
    }
}