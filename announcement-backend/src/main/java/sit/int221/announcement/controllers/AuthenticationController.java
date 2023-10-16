package sit.int221.announcement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.UserLogin;
import sit.int221.announcement.dtos.response.RefreshTokenResponse;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.services.AuthenticationService;
import sit.int221.announcement.utils.security.JwtUtil;

@RestController
@RequestMapping("/api/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("")
    public TokenResponse createToken(@RequestBody UserLogin request) {
        return service.createToken(request);
    }

    @GetMapping("")
    public RefreshTokenResponse getTokenByRefreshToken(@RequestHeader(value = "Authorization") String header) {
        String refreshToken = JwtUtil.getTokenFromHeader(header);
        if (refreshToken == null) return null;
        return service.createRefreshToken(refreshToken);
    }

}
