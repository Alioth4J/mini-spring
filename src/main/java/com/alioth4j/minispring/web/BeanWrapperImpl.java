package com.alioth4j.minispring.web;

import com.alioth4j.minispring.beans.PropertyEditor;
import com.alioth4j.minispring.beans.PropertyEditorRegistrySupport;
import com.alioth4j.minispring.beans.PropertyValue;
import com.alioth4j.minispring.beans.PropertyValues;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanWrapperImpl extends PropertyEditorRegistrySupport {

    Object wrappedObject;
    Class<?> clz;
    PropertyValues pvs;

    public BeanWrapperImpl(Object object) {
        registerDefaultEditors();
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    public void setBeanInstance(Object object) {
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    public Object getBeanInstance() {
        return wrappedObject;
    }

    public void setPropertyValue(PropertyValue pv) {
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
        if (pe == null) {
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        pe.setAsText((String) pv.getValue());
        propertyHandler.setValue(pe.getValue());
    }

    class BeanPropertyHandler {

        Method readMethod = null;
        Method writeMethod = null;
        Class<?> propertyClz = null;

        public BeanPropertyHandler(String propertyName) {
            Field field = null;
            try {
                field = clz.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            propertyClz = field.getType();
            try {
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        public Object getValue() {
            Object result = null;
            readMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return result;
        }

        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

}
