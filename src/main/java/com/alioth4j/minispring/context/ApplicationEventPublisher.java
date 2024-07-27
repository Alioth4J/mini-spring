package com.alioth4j.minispring.context;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

}
