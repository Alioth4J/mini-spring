package com.alioth4j.minispring.beans.factory.support;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.alioth4j.minispring.beans.factory.config.BeanDefinition;
import com.alioth4j.minispring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    ConfigurableListableBeanFactory parentBeanFactory;

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String bdName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition bd = this.getBeanDefinition(bdName);
            Class<? extends BeanDefinition> classToMatch = bd.getClass();
            if (type.isAssignableFrom(classToMatch)) {
                matchFound = true;
            } else {
                matchFound = false;
            }
            if (matchFound) {
                result.add(bdName);
            }
        }
        return result.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = null;
            try {
                beanInstance = getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }

    public void setParent(ConfigurableListableBeanFactory beanFactory) {
        this.parentBeanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object result = super.getBean(beanName);
        if (result == null) {
            return this.parentBeanFactory.getBean(beanName);
        }
        return result;
    }

}
