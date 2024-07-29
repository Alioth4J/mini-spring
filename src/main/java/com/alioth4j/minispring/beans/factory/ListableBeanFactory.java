package com.alioth4j.minispring.beans.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    boolean containsBean(String beanName);
    int getBeanDefinitionCount();
    String[] getBeanDefinitionNames();
    String[] getBeanNamesForType(Class<?> type);
    <T> Map<String, T> getBeansOfType(Class<T> type);

}
