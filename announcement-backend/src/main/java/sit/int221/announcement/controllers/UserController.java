package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.request.UserLoginDTO;
import sit.int221.announcement.dtos.request.UserRegisterDTO;
import sit.int221.announcement.dtos.request.UserEditDTO;
import sit.int221.announcement.dtos.response.UserResponseDTO;
import sit.int221.announcement.services.UserService;

import java.util.List;

@CrossOrigin(origins={"http://localhost:5173","http://intproj22.sit.kmutt.ac.th/kp1","http://25.38.200.142:5173","http://25.37.174.170:5173"})
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("")
    public List<UserResponseDTO> getUser() {
        return service.getUser();
    }

    @PostMapping("/match")
    public UserResponseDTO matchPassword(@Valid @RequestBody UserLoginDTO user) {
        return service.matchPassword(user);
    }
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Integer id) {
        return service.getResponseById(id);
    }
    @PostMapping("")
    public UserResponseDTO addUser(@Valid @RequestBody UserRegisterDTO user) {
        return service.addUser(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        service.deleteUser(id);
    }
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Integer id,@Valid @RequestBody UserEditDTO user) {
        return service.updateUser(id,user);
    }
}
