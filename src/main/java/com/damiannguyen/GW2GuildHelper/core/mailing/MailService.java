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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailService {
    private final BCryptPasswordEncoder encoder;
    //TODO: Handle better
    private final String API = "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";

    public void sendGreetings(User user){
        Email from = new Email("GW2GuildHelper@gmail.com");
        String subject = "Welcome to GW2GuildHelper!";
        Email to = new Email(user.getEmail());
        //TODO: better content!
        Content content = new Content("text/plain", "Welcome " + user.getUsername() + "!");
        sendEmail(from, subject, to, content);
    }

    public void sendPassword(User user){
        Email from = new Email("GW2GuildHelper@gmail.com");
        String subject = "Welcome to GW2GuildHelper!";
        Email to = new Email(user.getEmail());
        //TODO: better content! + Decrypt password?
        Content content = new Content("text/plain", "Welcome " + user.getUsername() + "!\n" + "Your password is: " + user.getPassword());
        sendEmail(from, subject, to, content);
    }

    private void sendEmail(Email from, String subject, Email to, Content content){
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sendGrid = new SendGrid(API);
        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
        }catch (IOException e){
            log.error("Error while sending mail to " + to.getEmail());
        }
    }
}
