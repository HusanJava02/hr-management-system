package uz.pdp.hrmanagementsystem.service.eventlisteners;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import uz.pdp.hrmanagementsystem.emailService.EmailSender;
import uz.pdp.hrmanagementsystem.entities.enums.NotificationType;
import uz.pdp.hrmanagementsystem.service.publisher.NewUserPublishEvent;
import uz.pdp.hrmanagementsystem.service.publisher.NotificationPublishEvent;

@Log4j2
@Component
public class RegistrationListener {

    @Autowired
    private EmailSender emailSender;

    @EventListener
    @Async
    public void onRegistrationSuccess(NotificationPublishEvent event) {
        try{
            if (event.getClass().getName().equals(NewUserPublishEvent.class.getName())) {
                emailSender.sendEmail(event,NotificationType.NEW_USER);
            }else {
                emailSender.sendEmail(event, NotificationType.ACTIVATE_ACCOUNT);
            }
            log.info(String.format("Email successfully sent: %s", event.getEmail()));
        }catch (Exception e){
            log.error("Email cant sent, error: ",e);
        }

    }


    @EventListener
    @Async
    public void onNewUserCreated(NewUserPublishEvent event) {
        try{
            emailSender.sendEmail(event,NotificationType.NEW_USER);
        }catch (Exception e){
            log.error("Email cant sent, error: ",e);
        }
    }
}
