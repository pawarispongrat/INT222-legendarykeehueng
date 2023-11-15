package sit.int221.announcement.services.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.SubscriptionRequest;
import sit.int221.announcement.services.subscription.OtpService;
import sit.int221.announcement.utils.modules.EmailModule;
import sit.int221.announcement.utils.properties.EmailProperties;

@Service
public class EmailService {

    @Autowired
    private EmailProperties properties;

}
