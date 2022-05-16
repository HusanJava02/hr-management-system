package uz.pdp.hrmanagementsystem.emailService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import uz.pdp.hrmanagementsystem.entities.enums.NotificationType;
import uz.pdp.hrmanagementsystem.service.publisher.NewUserPublishEvent;
import uz.pdp.hrmanagementsystem.service.publisher.NotificationPublishEvent;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Log4j2
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.account.activation.url}")
    private String activationAccountURL;

    public void sendEmail(NotificationPublishEvent event, NotificationType notificationType) throws IOException, MessagingException {
        switch (notificationType) {
            case ACTIVATE_ACCOUNT:
                System.out.println("salom");
                sendActivateAccountEmail(event);
                break;
            case NEW_USER:
                if (event instanceof NewUserPublishEvent) {
                    NewUserPublishEvent newUserPublishEvent = (NewUserPublishEvent) event;
                    sendNewUserRegistrationEmail(newUserPublishEvent);
                }
                break;
        }
    }

    private void sendNewUserRegistrationEmail(NewUserPublishEvent event) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("noreply@mail.com");
        mimeMessageHelper.setTo(event.getEmail());
        mimeMessageHelper.setSubject(NotificationType.NEW_USER.getInfo());
        mimeMessageHelper.setText(getContentNewUserHtml(event.getPassword()), true);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> javaMailSender.send(mimeMessage));
    }


    private void sendActivateAccountEmail(NotificationPublishEvent event) throws IOException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("noreply@mail.com");
        mimeMessageHelper.setTo(event.getEmail());
        mimeMessageHelper.setSubject(NotificationType.ACTIVATE_ACCOUNT.getInfo());
        mimeMessageHelper.setText(getContentFromTemplate(NotificationType.ACTIVATE_ACCOUNT, populateUrl(this.activationAccountURL,event.getEmail(),event.getVerificationCode())), true);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> javaMailSender.send(mimeMessage));
    }

    private String getContentFromTemplate(NotificationType notificationType, String url) throws IOException {
        File file = ResourceUtils.getFile(String.format("classpath:%s%s%s", "email-templates/", notificationType.toStringLower(), ".html"));
        String content = readFromFile(file);
        content = content.replace("${", "");
        content = content.replace(notificationType.toString(), url);
        content = content.replace("}", "");
        return content;
    }

    private String getContentNewUserHtml(String password) throws IOException {
        File file = ResourceUtils.getFile(String.format("classpath:%s%s%s", "email-templates/", NotificationType.NEW_USER.toStringLower(), ".html"));
        String content = readFromFile(file);
        content = content.replace("${", "");
        content = content.replace("PASSWORD",password);
        content = content.replace("}", "");
        return content;
    }




    private String readFromFile(File file) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder content = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }

    /*http://localhost:8081/api/auth/activate/account?uniqueId=${code}&email=${email}*/
    private String populateUrl(String rawUrl,String email,String verificationCode) {
        rawUrl = rawUrl.replace("CODE", verificationCode);
        rawUrl = rawUrl.replace("EMAIL",email);
        return rawUrl;
    }


}
