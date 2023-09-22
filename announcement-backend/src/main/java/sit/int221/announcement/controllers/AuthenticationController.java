package sit.int221.announcement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.announcement.dtos.request.TokenRequest;
import sit.int221.announcement.dtos.request.UserLoginDTO;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.exceptions.list.UserException;
import sit.int221.announcement.services.AuthenticationService;

@RestController
@RequestMapping("/api/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("")
    public TokenResponse createToken(@RequestBody UserLoginDTO request) {
        return service.createToken(request);
    }

}
