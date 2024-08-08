package com.alioth4j.minispring.aop;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}
