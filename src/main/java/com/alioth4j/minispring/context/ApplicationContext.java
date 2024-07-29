package com.alioth4j.minispring.context;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.ListableBeanFactory;
import com.alioth4j.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.alioth4j.minispring.beans.factory.config.ConfigurableBeanFactory;
import com.alioth4j.minispring.beans.factory.config.ConfigurableListableBeanFactory;
import com.alioth4j.minispring.core.env.Environment;
import com.alioth4j.minispring.core.env.EnvironmentCapable;

public interface ApplicationContext
        extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {

    String getApplicationName();
    long getStartupDate();
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
    Environment getEnvironment();
    void setEnvironment(Environment environment);
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
    void refresh() throws BeansException, IllegalStateException;
    void close();
    boolean isActive();

}
