package com.alioth4j.minispring.beans;

/**
 * 对应构造器注入
 */
public class ArgumentValue {

    private String type;
    private String name;
    private Object value;

    public ArgumentValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public ArgumentValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
