package com.alioth4j.minispring.beans;

import com.alioth4j.minispring.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析XML文件，注册bean
 */
public class XmlBeanDefinitionReader {

    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            // <bean>内的属性
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            // <bean>中嵌套的标签
            // <constructor-arg>
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues avs = new ArgumentValues();
            for (Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                avs.addArgumentValue(new ArgumentValue(aType, aName, aValue));
            }
            beanDefinition.setConstructorArgumentValues(avs);

            // <property>
            List<Element> propertyElements = element.elements("property");
            PropertyValues pvs = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                pvs.addPropertyValue(new PropertyValue(pType, pName, pValue, isRef));
            }
            beanDefinition.setPropertyValues(pvs);
            beanDefinition.setDependsOn(refs.toArray(new String[0]));

            this.simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }

}
