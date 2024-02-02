package sit.int221.announcement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.UserLogin;
import sit.int221.announcement.dtos.request.UserRegister;
import sit.int221.announcement.dtos.request.UserEdit;
import sit.int221.announcement.dtos.response.UserResponse;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.models.User;
import sit.int221.announcement.repositories.UserRepository;
import sit.int221.announcement.utils.ListMapper;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ListMapper mappers;
    @Autowired
    private PasswordEncoder encoder;


    private UserResponse getResponse(User users) {
        return mapper.map(users, UserResponse.class);
    }
    private List<UserResponse> getResponse(List<User> users) {
        return mappers.mapList(users, UserResponse.class,mapper);
    }
    public UserResponse getResponseById(Integer id) {
        return mapper.map(getUserById(id), UserResponse.class);
    }

    public List<UserResponse> getUser() {
        return getResponse(repository.findAll());
    }

    public UserResponse matchPassword(UserLogin post) {
        User user = getUserByUsername(post.getUsername());
        boolean isMatch = encoder.matches(post.getPassword(),user.getPassword());
        if (!isMatch) throw new AuthorizedException("user","Password not matched");
        else return getResponse(user);
    }

    public UserResponse addUser(UserRegister post){
        User user = mapper.map(post,User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.trim();
        User save = repository.saveAndFlush(user);
        repository.refresh(save);
        return getResponse(save);
    }

    public void deleteUser(Integer id){
        User user = getUserById(id);
        repository.delete(user);
    }

    public UserResponse updateUser(Integer id, UserEdit post){
        User user = getUserById(id);
        mapper.map(post,user);
        user.trim();
        User save = repository.saveAndFlush(user);
        repository.refresh(save);
        return getResponse(save);
    }


    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException("User id not found"));
    }
    public User getUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new ItemNotFoundException("username","The specified username does not exist"));
    }
    public User getUserByUsernameOrNull(String username) {
        return repository.findByUsername(username).orElse(null);
    }
    public User getUserByEmailOrNull(String email) {
        return repository.findByEmail(email).orElse(null);
    }

}
