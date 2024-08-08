package com.alioth4j.minispring.aop;

public interface Advisor {

    Advice getAdvice();
    MethodInterceptor getMethodInterceptor();
    void setMethodInterceptor(MethodInterceptor methodInterceptor);

}
