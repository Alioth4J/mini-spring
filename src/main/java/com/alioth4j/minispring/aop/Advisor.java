package com.alioth4j.minispring.aop;

public interface Advisor {

    MethodInterceptor getMethodInterceptor();
    void setMethodInterceptor(MethodInterceptor methodInterceptor);

}
