package uz.pdp.hrmanagementsystem.service.publisher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPublishEvent {
    private String email;
    private String verificationCode;
    private String message;
}
