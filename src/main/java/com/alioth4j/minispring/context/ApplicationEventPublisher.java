package com.alioth4j.minispring.context;

import java.util.EventListener;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);

}
