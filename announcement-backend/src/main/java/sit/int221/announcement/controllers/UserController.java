package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.AnnouncementDTO;
import sit.int221.announcement.dtos.request.AnnouncementRequestDTO;
import sit.int221.announcement.dtos.request.UserRequestDTO;
import sit.int221.announcement.models.Announcement;
import sit.int221.announcement.models.User;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.UserService;
import sit.int221.announcement.utils.Modes;

import java.util.List;

@CrossOrigin(origins={"http://localhost:5173","http://intproj22.sit.kmutt.ac.th/kp1","http://25.38.200.142:5173","http://25.37.174.170:5173"})
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("")
    public List<User> getUser() {
        return service.getUser();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return service.getUserById(id);
    }
    @PostMapping("")
    public User addUser(@Valid @RequestBody UserRequestDTO User) {
        return service.addUser(User);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        service.deleteUser(id);
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id,@Valid @RequestBody UserRequestDTO User) {
        return service.updateUser(id,User);
    }
}
