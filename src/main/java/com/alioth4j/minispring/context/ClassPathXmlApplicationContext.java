package com.alioth4j.minispring.context;

import com.alioth4j.minispring.beans.*;
import com.alioth4j.minispring.beans.factory.BeanFactory;
import com.alioth4j.minispring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.alioth4j.minispring.beans.factory.config.AutowireCapableBeanFactory;
import com.alioth4j.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.alioth4j.minispring.beans.factory.support.SimpleBeanFactory;
import com.alioth4j.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import com.alioth4j.minispring.core.ClassPathXmlResource;
import com.alioth4j.minispring.core.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 作为外部集成包装
 * 加载资源（读取、解析XML文件），构建BeanDefinition，注入到BeanFactory中
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    AutowireCapableBeanFactory beanFactory;

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory bf = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = bf;
        if (isRefresh) {
            refresh();
        }
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

    private void refresh() {
        registerBeanPostProcessors(this.beanFactory);
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory bf) {
//        if (supportAutowire) {
            bf.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
//        }
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }

}