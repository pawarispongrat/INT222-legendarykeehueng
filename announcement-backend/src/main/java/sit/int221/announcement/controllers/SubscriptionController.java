package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.SubscriptionRequest;
import sit.int221.announcement.dtos.request.UnsubscribeRequest;
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

    @PostMapping("/subscribe/{otp}")
    public ResponseMessage subscribeWithOTP(@PathVariable String otp,@Valid @RequestBody SubscriptionRequest request) {
        return service.subscribe(request,otp) ?
                new ResponseMessage("Subscribed") :
                new ResponseMessage("Invalid OTP");
    }

    @PostMapping("/unsubscribe")
    public ResponseMessage unsubscribe(@Valid @RequestBody UnsubscribeRequest request) {
        return service.unsubscribe(request)?
                new ResponseMessage("Unsubscribed") :
                new ResponseMessage("Invalid unsubscribed");
    }
}
