package com.alioth4j.minispring.context;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.alioth4j.minispring.beans.factory.config.BeanPostProcessor;
import com.alioth4j.minispring.beans.factory.config.ConfigurableListableBeanFactory;
import com.alioth4j.minispring.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private Environment environment;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    private long startupDate;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private ApplicationEventPublisher applicationEventPublisher;

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public String[] getSingletonNames() {
        return getBeanFactory().getSingletonNames();
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return getBeanFactory().containsSingleton(beanName);
    }

    @Override
    public Object getSingleton(String beanName) {
        return getBeanFactory().getSingleton(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        getBeanFactory().registerSingleton(beanName, singletonObject);
    }

    @Override
    public Class<?> getType(String name) {
        return getBeanFactory().getType(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return getBeanFactory().isPrototype(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }

//    @Override
//    public void registerBean(String beanName, Object obj) {
//        getBeanFactory().registerBean(beanName, obj);
//    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return getBeanFactory().getDependenciesForBean(beanName);
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return getBeanFactory().getDependentBeans(beanName);
    }

    @Override
    public void registerSingleton(String beanName, String dependentBeanName) {
        getBeanFactory().registerSingleton(beanName, dependentBeanName);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanFactory().addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public boolean containsBean(String beanName) {
        return getBeanFactory().containsBean(beanName);
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        postProcessBeanFactory(getBeanFactory());
        registerBeanPostProcessors(getBeanFactory());
        initApplicationEventPublisher();
        onRefresh();
        registerListeners();
        finishRefresh();
    }

    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory);
    abstract void initApplicationEventPublisher();
    abstract void onRefresh();
    abstract void registerListeners();
    abstract void finishRefresh();

    @Override
    public void close() {
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

}
