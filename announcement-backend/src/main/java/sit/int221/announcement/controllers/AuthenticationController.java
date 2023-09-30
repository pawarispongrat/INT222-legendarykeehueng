package sit.int221.announcement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.RefreshTokenRequest;
import sit.int221.announcement.dtos.request.UserLoginDTO;
import sit.int221.announcement.dtos.response.RefreshTokenResponse;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.services.AuthenticationService;
import sit.int221.announcement.utils.security.JwtUtil;

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
    public RefreshTokenResponse getTokenByRefreshToken(@RequestHeader("Authorization") String header) {
        if (!JwtUtil.isBearer(header)) throw new AuthorizedException("header");
        String refreshToken = JwtUtil.getTokenFromHeader(header);
        return service.createRefreshToken(refreshToken);
    }

}
