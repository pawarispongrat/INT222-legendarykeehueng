package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.SubscriptionRequest;
import sit.int221.announcement.services.subscription.OtpService;
import sit.int221.announcement.utils.ResponseMessage;

@RestController
@RequestMapping("/api/subscription")
public class EmailController {

    @Autowired
    private OtpService service;

    @PostMapping
    public ResponseMessage subscribe(@Valid @RequestBody SubscriptionRequest request) {
        return service.sendOtp(request.getSubscriberEmail()) ?
                new ResponseMessage("Mail is sent") :
                new ResponseMessage("Mail is not sent");
    }

    @GetMapping("/{email}/{otp}")
    public ResponseMessage validate(@PathVariable String email,@PathVariable String otp) {
        return service.verifyOtp(email,otp) ?
                new ResponseMessage("Valid Otp") :
                new ResponseMessage("Not Valid Otp");
    }
}
