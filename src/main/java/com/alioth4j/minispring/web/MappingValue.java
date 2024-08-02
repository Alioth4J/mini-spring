package com.alioth4j.minispring.web;

public class MappingValue {

    String uri;
    String clz;
    String method;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public MappingValue(String uri, String clz, String method) {
        this.uri = uri;
        this.clz = clz;
        this.method = method;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
