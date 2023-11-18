package sit.int221.announcement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.SubscriptionRequest;
import sit.int221.announcement.dtos.request.UnsubscribeRequest;
import sit.int221.announcement.enumeration.SubscribeNotify;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.list.MailSentException;
import sit.int221.announcement.models.Category;
import sit.int221.announcement.models.Subscription;
import sit.int221.announcement.models.ids.SubscriptionId;
import sit.int221.announcement.repositories.SubscriptionRepository;
import sit.int221.announcement.utils.modules.EmailModule;
import sit.int221.announcement.utils.properties.EmailProperties;
import sit.int221.announcement.utils.security.OtpUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SubscriptionService {

    @Autowired
    private OtpUtil util;
    @Autowired
    private EmailProperties properties;
    @Autowired
    private SubscriptionRepository repository;


    public SubscriptionRequest sendOtp(SubscriptionRequest request) {
        String email = request.getSubscriberEmail();
        EmailModule module = new EmailModule(properties);
        String otp = util.generateHOTP(email);
        boolean isSent = module.sendEmail(email,"HelloOtp", otp);
        if (!isSent) throw new MailSentException();
        return request;
    }

    public void sendSubscribeMail(SubscribeNotify notify, Category category) {
        EmailModule module = new EmailModule(properties);
        getEmails(category.getCategoryId()).forEach(email -> {
            module.sendEmail(email,"Hello Subscribe","Subscribe " + notify.toString() + " :" + category.getCategoryName());
        });
    }


    public boolean subscribe(SubscriptionRequest request,String otp) {
        String email = request.getSubscriberEmail();
        boolean isValid = util.validateOtp(email,otp);
        if (isValid) repository.saveAndFlush(new Subscription(email,request.getCategoryId()));
        return isValid;
    }

    public boolean unsubscribe(UnsubscribeRequest request) {
        String email = request.getSubscriberEmail();
        Integer categoryId = request.getCategoryId();
        SubscriptionId id = new SubscriptionId(email,categoryId);
        boolean exists = repository.existsById(id);
        if (!exists) throw new ItemNotFoundException("subscriptionId");
        repository.deleteById(id);
        return true;
    }

    public boolean isSubscribe(String email,int categoryId) {
        SubscriptionId id = new SubscriptionId(email,categoryId);
        return repository.existsById(id);
    }
    public List<String> getEmails(Integer categoryId) {
        return repository.findEmails(categoryId).orElseThrow(() -> new ItemNotFoundException("categoryId"));
    }

}
