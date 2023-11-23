package sit.int221.announcement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.subscription.SubscriptionOtpRequest;
import sit.int221.announcement.dtos.request.subscription.SubscriptionRequest;
import sit.int221.announcement.dtos.request.subscription.UnsubscribeRequest;
import sit.int221.announcement.dtos.response.announcement.AnnouncementAdminResponse;
import sit.int221.announcement.enumeration.SubscribeNotify;
import sit.int221.announcement.exceptions.list.InvalidOtpException;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.list.MailSentException;
import sit.int221.announcement.models.Announcement;
import sit.int221.announcement.models.Category;
import sit.int221.announcement.models.Subscription;
import sit.int221.announcement.models.ids.SubscriptionId;
import sit.int221.announcement.repositories.SubscriptionRepository;
import sit.int221.announcement.utils.ResponseMessage;
import sit.int221.announcement.utils.modules.EmailModule;
import sit.int221.announcement.utils.properties.EmailProperties;
import sit.int221.announcement.utils.security.MD5;
import sit.int221.announcement.utils.security.OtpUtil;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private OtpUtil util;
    @Autowired
    private SubscriptionRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EmailModule module;

    @Value("${host.domain}")
    private String DOMAIN;


    public SubscriptionRequest sendOtp(SubscriptionRequest request) {
        String email = request.getSubscriberEmail();
        List<Integer> categoryIds = request.getCategoryId();
        String otp = util.generateHOTP(email,categoryIds);
        module.sendEmail(email,"SAS Announcement: Verify email from otp", otp);
        return request;
    }

    public void sendSubscribeMail(Announcement announcement, SubscribeNotify notify, Category category) {
        StringBuilder sb = new StringBuilder();
        String[] bodies = new String[]{
                String.format("Description: %s",announcement.getAnnouncementDescription()), " ",
                String.format("<a href='%s/announcement/%s'>Click here to view %s announcement</a>", DOMAIN, announcement.getId(),notify.toString()), " ",
        };
        for (String body : bodies) module.appendBody(sb, body);

        getEmails(category.getCategoryId()).forEach(email -> {
            String hashEmail = new MD5(email).encode();
            String unsubscribeLink = String.format("%s/unsubscribe?email=%s&hash=%s",DOMAIN,email,hashEmail);
            String unsubscribe = String.format("<a href='%s'>Click here to unsubscribe</a>",unsubscribeLink);
            String subject = String.format("%s, SAS Subscription [%s หมวดหมู่ (%s): %s]",
                    email,
                    notify,
                    category.getCategoryName(),
                    announcement.getAnnouncementTitle()
            );
            module.appendBody(sb, unsubscribe);
            module.sendEmail(email,subject,sb.toString());
        });
    }


    public ResponseMessage subscribe(SubscriptionOtpRequest request) {
        String email = request.getSubscriberEmail();
        String otp = request.getOtp();
        List<Integer> categoryIds = util.getCategoryIdsByOtp(email,otp);
        if (categoryIds == null) throw new InvalidOtpException();

        ResponseMessage message = new ResponseMessage("SubscriptionCategory");
        StringBuilder sb = new StringBuilder();
        for (Integer categoryId : categoryIds) {
            boolean isSubscribe = isSubscribe(email,categoryId);
            if (!isSubscribe) repository.saveAndFlush(new Subscription(email,categoryId));
            message.addExist(categoryId.toString(), isSubscribe);

            String name = categoryService.getCategoryByIdOrNull(categoryId).getCategoryName();
            if (name != null)
                module.appendBody(sb,isSubscribe ?
                    "You are already subscribe CATEGORY: " + name :
                    "You have subscribe new CATEGORY: " + name
            );
        }

        module.sendEmail(email,"SAS Announcement: Thank you " + email + " for subscribe.", sb.toString());
        return message;
    }

    public boolean unsubscribe(UnsubscribeRequest request) {
        String email = request.getSubscriberEmail();
        String hashEmail = request.getHashEmail();
        List<Integer> categoryIds = request.getCategoryId();
        MD5 md5 = new MD5(email);
        if (!md5.matches(hashEmail)) return false;
        StringBuilder sb = new StringBuilder();

        for (Integer categoryId : categoryIds) {
            SubscriptionId id = new SubscriptionId(email, categoryId);
            boolean exists = repository.existsById(id);
            if (!exists) continue;
            module.appendBody(sb,"You have unsubscribe CATEGORY: " + categoryId);
            repository.deleteById(id);
        }

        module.sendEmail(email,"SAS Announcement: You have unsubscribe some category", sb.toString());
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
