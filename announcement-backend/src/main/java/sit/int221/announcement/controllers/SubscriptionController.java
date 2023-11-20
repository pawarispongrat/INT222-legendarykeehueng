package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.subscription.SubscriptionOtpRequest;
import sit.int221.announcement.dtos.request.subscription.SubscriptionRequest;
import sit.int221.announcement.dtos.request.subscription.UnsubscribeRequest;
import sit.int221.announcement.services.SubscriptionService;
import sit.int221.announcement.utils.ResponseMessage;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping("/subscribe")
    public SubscriptionRequest subscribe(@Valid @RequestBody SubscriptionRequest request) {
        return service.sendOtp(request);
    }

    @PostMapping("/subscribe-otp")
    public ResponseMessage subscribeWithOTP(@Valid @RequestBody SubscriptionOtpRequest request) {
        return service.subscribe(request);
    }

    @PostMapping("/unsubscribe")
    public ResponseMessage unsubscribe(@Valid @RequestBody UnsubscribeRequest request) {
        return service.unsubscribe(request)?
                new ResponseMessage("Unsubscribed") :
                new ResponseMessage("Invalid unsubscribed");
    }
}
