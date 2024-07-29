package com.alioth4j.minispring.beans.factory.config;

import com.alioth4j.minispring.beans.BeansException;
import com.alioth4j.minispring.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            beanPostProcessor.setBeanFactory(this);
            try {
                result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            if (result == null) {
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            beanPostProcessor.setBeanFactory(this);
            try {
                beanPostProcessor.postProcessAfterInitialization(result, beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
            if (result == null) {
                return result;
            }
        }
        return result;
    }
}
