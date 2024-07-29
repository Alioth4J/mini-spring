package com.alioth4j.minispring.context;

import java.util.ArrayList;
import java.util.List;

public class SimpleApplicationEventPublisher implements ApplicationEventPublisher{

    List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : this.listeners) {
            listener.onApplicationEvent(event);
        }
    }

}
