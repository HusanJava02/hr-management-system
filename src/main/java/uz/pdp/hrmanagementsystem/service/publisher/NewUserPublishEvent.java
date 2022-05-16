package uz.pdp.hrmanagementsystem.service.publisher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUserPublishEvent extends NotificationPublishEvent{
    private String email;
    private String password;
}
