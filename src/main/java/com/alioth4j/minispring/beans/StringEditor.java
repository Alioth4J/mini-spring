package com.alioth4j.minispring.beans;

public class StringEditor implements PropertyEditor {

    private Class<String> strClass;
    private String strFormat;
    private boolean allowEmpty;
    private Object value;

    public StringEditor(Class<String> strClass, String strFormat, boolean allowEmpty) {
        this.strClass = strClass;
        this.strFormat = strFormat;
        this.allowEmpty = allowEmpty;
    }

    public StringEditor(Class<String> strClass, boolean allowEmpty) {
        this(strClass, "", allowEmpty);
    }

    @Override
    public void setAsText(String text) {
        setValue(text);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getAsText() {
        return this.value.toString();
    }

}
