package com.alioth4j.minispring.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory的一个简单实现类
 * 负责Bean的容器化管理
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    public SimpleBeanFactory() {
    }

    /**
     * 激活IoC容器
     */
    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);

        if (singleton == null) {
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                BeanDefinition bd = this.beanDefinitionMap.get(beanName);
                singleton = createBean(bd);
                this.registerBean(beanName, singleton);

                // BeanPostProcessor
                // step1: postProcessBeforeInitialization
                // step2: afterPropertiesSet
                // step3: init-method
                // step4: postProcessAfterInitialization
            }
        }
        if (singleton == null) {
            throw new BeansException("No such bean: " + beanName);
        }
        return singleton;
    }

    private Object createBean(BeanDefinition bd) {
        Object obj = doCreateBean(bd);
        // 放入earlySingletonObjects中
        this.earlySingletonObjects.put(bd.getId(), obj);
        Class<?> clz = null;
        try {
            clz = Class.forName(bd.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        handleProperties(bd, clz, obj);
        return obj;
    }

    /**
     * 构造器注入创建Bean实例
     * @param bd
     * @return
     */
    private Object doCreateBean(BeanDefinition bd) {
        Class<?> clz = null;
        try {
            clz = Class.forName(bd.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object obj = null;

        // 构造器注入相关
        ArgumentValues argumentValues = bd.getConstructorArgumentValues();
        if (!argumentValues.isEmpty()) {
            // 构造器注入
            Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
            Object[] paramValues = new Object[argumentValues.getArgumentCount()];
            for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                    paramTypes[i] = String.class;
                    paramValues[i] = argumentValue.getValue();
                } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                    paramTypes[i] = Integer.class;
                    paramValues[i] = argumentValue.getValue();
                } else if ("int".equals(argumentValue.getType())) {
                    paramTypes[i] = int.class;
                    paramValues[i] = argumentValue.getValue();
                } else {
                    paramTypes[i] = String.class;
                    paramValues[i] = argumentValue.getValue();
                }
            }
            Constructor<?> con = null;
            try {
                con = clz.getConstructor(paramTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                obj = con.newInstance(paramValues);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                obj = clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * setter注入相关
     * @param bd
     * @param clz
     * @param obj
     */
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        PropertyValues propertyValues = bd.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            // setter方法注入
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String name = propertyValue.getName();
                String type = propertyValue.getType();
                Object value = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if (!isRef) {
                    if ("String".equals(type) || "java.lang.String".equals(type)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(type)) {
                        paramTypes[0] = int.class;
                    } else {
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = value;
                } else {
                    try {
                        paramTypes[0] = Class.forName(type);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        paramValues[0] = getBean((String)value);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }
                }
                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method setter = null;
                try {
                    setter = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    setter.invoke(obj, paramValues);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        // 加入到2个集合中
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
        // 不是懒加载现在就加载
        if (!bd.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        // 从所有集合中移除
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        singletons.remove(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

}
