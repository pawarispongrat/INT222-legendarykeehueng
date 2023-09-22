package sit.int221.announcement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.RefreshTokenRequest;
import sit.int221.announcement.dtos.request.UserLoginDTO;
import sit.int221.announcement.dtos.response.RefreshTokenResponse;
import sit.int221.announcement.dtos.response.TokenResponse;
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

    @GetMapping("")
    public RefreshTokenResponse getTokenByRefreshToken(@RequestBody RefreshTokenRequest request) {
        return service.getTokenFromRefreshToken(request);
    }

}
