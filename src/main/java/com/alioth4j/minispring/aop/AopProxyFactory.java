package com.alioth4j.minispring.aop;

public interface AopProxyFactory {

    AopProxy createAopProxy(Object target, Advisor advisor);

}
