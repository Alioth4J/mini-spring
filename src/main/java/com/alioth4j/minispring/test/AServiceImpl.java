package com.alioth4j.minispring.test;

public class AServiceImpl implements AService {

    @Override
    public void sayHello() {
        System.out.println("Hello World!");
        System.out.println("Hello Spring!");
    }

}
