package sit.int221.announcement.utils.modules;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import sit.int221.announcement.utils.properties.EmailProperties;

import java.util.Properties;

public class EmailModule {

    private final EmailProperties properties;
    public EmailModule(EmailProperties properties) {
        this.properties = properties;
    }

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

    public boolean sendEmail(String email, String subject, String body) {
        try {
            Address source = new InternetAddress(properties.getUsername());
            Address destination = new InternetAddress(email);
            Session session = Session.getInstance(getProperties(), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getUsername(),properties.getPassword());
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(source);
            message.setRecipient(Message.RecipientType.TO, destination);
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return true;
        } catch (MailException | MessagingException e) {
            return false;
        }
    }


}
