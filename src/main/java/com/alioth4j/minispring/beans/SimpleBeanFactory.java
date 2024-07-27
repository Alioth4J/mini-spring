package com.alioth4j.minispring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanFactory的一个简单实现类
 * 负责Bean的容器化管理
 */
public class SimpleBeanFactory implements BeanFactory {

    // beanDefinitions和beanNames的index对应
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = singletons.get(beanName);
        if (singleton == null) {
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new BeansException("No such bean: " + beanName);
            } else {
                BeanDefinition bd = beanDefinitions.get(i);
                try {
                    singleton = Class.forName(bd.getClassName()).newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                singletons.put(bd.getId(), singleton);
            }
        }
        return singleton;
    }

}
