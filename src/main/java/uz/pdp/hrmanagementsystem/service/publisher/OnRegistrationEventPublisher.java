package uz.pdp.hrmanagementsystem.service.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OnRegistrationEventPublisher {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void publish(NotificationPublishEvent event){
        publisher.publishEvent(event);
    }

}
