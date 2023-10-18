package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.UserLogin;
import sit.int221.announcement.dtos.request.UserRegister;
import sit.int221.announcement.dtos.request.UserEdit;
import sit.int221.announcement.dtos.response.UserResponse;
import sit.int221.announcement.exceptions.list.ForbiddenException;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.UserService;
import sit.int221.announcement.utils.components.UserComponent;
import sit.int221.announcement.utils.security.JwtTokenUtil;
import sit.int221.announcement.utils.security.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private AnnouncementService announce;

    @Autowired
    private UserComponent component;
    @GetMapping("")
    public List<UserResponse> getUser() {
        return service.getUser();
    }

    @PostMapping("/match")
    public UserResponse matchPassword(@Valid @RequestBody UserLogin user) {
        return service.matchPassword(user);
    }
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return service.getResponseById(id);
    }
    @PostMapping("")
    public UserResponse addUser(@Valid @RequestBody UserRegister user) {
        return service.addUser(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        String username = component.getSubject();
        if (username == null) return;
        if (announce.updateAnnouncementOwnerByUserId(id,username) == null) throw new ForbiddenException("User","Cannot delete your user");
        service.deleteUser(id);
    }
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Integer id, @Valid @RequestBody UserEdit user) {
        return service.updateUser(id,user);
    }
}
