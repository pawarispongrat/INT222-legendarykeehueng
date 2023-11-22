package sit.int221.announcement.utils.modules;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sit.int221.announcement.exceptions.list.MailSentException;
import sit.int221.announcement.utils.properties.EmailProperties;

import java.util.Properties;

@Service
public class EmailModule {


    @Autowired
    private EmailProperties properties;

    public Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", properties.getHost());
        props.put("mail.smtp.port", properties.getPort());
        props.put("mail.transport.protocol", properties.getTransportProtocol());
        props.put("mail.smtp.auth", properties.getSmtpAuth());
        props.put("mail.smtp.starttls.enable", properties.getSmtpTlsEnable());
        props.put("mail.debug", properties.getDebug());
        return props;
    }

    @Async
    public void sendEmail(String email, String subject, String body) {
        try {
            InternetAddress source = new InternetAddress(properties.getUsername());
            InternetAddress destination = new InternetAddress(email);
            Session session = Session.getInstance(getProperties(), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getUsername(),properties.getPassword());
                }
            });
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message,false,"UTF-8");
            helper.setFrom(source);
            helper.setTo(destination);
            helper.setSubject(subject);
            helper.setText(body,true);
            Transport.send(message);
        } catch (MailException | MessagingException e) {
            throw new MailSentException();
        }
    }


}
