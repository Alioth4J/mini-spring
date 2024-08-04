package com.alioth4j.minispring.beans;

public interface PropertyEditor {

    void setAsText(String text);
    void setValue(Object value);
    Object getValue();
    String getAsText();

}
