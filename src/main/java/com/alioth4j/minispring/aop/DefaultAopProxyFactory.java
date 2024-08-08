package com.alioth4j.minispring.aop;

public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(Object target) {
        return new JdkDynamicAopProxy(target);
    }

}
