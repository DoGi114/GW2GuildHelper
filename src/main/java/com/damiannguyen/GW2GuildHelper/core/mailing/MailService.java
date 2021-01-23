package com.damiannguyen.GW2GuildHelper.core.mailing;

import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class MailService {
    private final String emailFrom;
    private final SendGrid sendGridApi;

    public MailService(
            @Value("${GW2GuildHelperApp.sendgrid.apikey}") String sendgridApiKey,
            @Value("${GW2GuildHelperApp.sendgrid.emailFrom}") String emailFrom) {
        this.emailFrom = emailFrom;
        this.sendGridApi = new SendGrid(sendgridApiKey);
    }

    public void sendGreetings(User user){
        Email from = new Email(emailFrom);
        String subject = "Welcome to GW2GuildHelper!";
        Email to = new Email(user.getEmail());
        //TODO: better content!
        Content content = new Content("text/plain", "Welcome " + user.getUsername() + "!");
        sendEmail(from, subject, to, content);
    }

    public void sendPasswordReseted(User user){
        Email from = new Email(emailFrom);
        String subject = "Welcome to GW2GuildHelper!";
        Email to = new Email(user.getEmail());
        //TODO: better content!
        Content content = new Content("text/plain", "Welcome " + user.getUsername() + "!\n" + "reset password with this link: <a href='www.gw2guildhelper.app/reset-password/" + user.getUuid() + "'>click!</a>");
        sendEmail(from, subject, to, content);
    }

    private void sendEmail(Email from, String subject, Email to, Content content){
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridApi.api(request);
        }catch (IOException e){
            log.error("Error while sending mail to " + to.getEmail());
        }
    }
}
