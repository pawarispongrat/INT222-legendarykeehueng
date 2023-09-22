package sit.int221.announcement.services;

import jakarta.validation.ConstraintViolationException;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.UserLoginDTO;
import sit.int221.announcement.dtos.request.UserRegisterDTO;
import sit.int221.announcement.dtos.request.UserEditDTO;
import sit.int221.announcement.dtos.response.UserResponseDTO;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.models.User;
import sit.int221.announcement.repositories.UserRepository;
import sit.int221.announcement.utils.security.Argon;
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

    private UserResponseDTO getResponse(User users) {
        return mapper.map(users,UserResponseDTO.class);
    }
    private List<UserResponseDTO> getResponse(List<User> users) {
        return mappers.mapList(users,UserResponseDTO.class,mapper);
    }
    public UserResponseDTO getResponseById(Integer id) {
        return mapper.map(getUserById(id),UserResponseDTO.class);
    }

    public List<UserResponseDTO> getUser() {
        return getResponse(repository.findAll());
    }

    public UserResponseDTO matchPassword(UserLoginDTO post) {
        User user = getUserByUsername(post.getUsername());
        boolean isMatch = new Argon().match(post.getPassword(),user.getPassword());
        if (!isMatch) throw new AuthorizedException("user","Password not matched");
        else return getResponse(user);
    }

    public UserResponseDTO addUser(UserRegisterDTO post){
        Argon argon = new Argon();
        String encoded = argon.encode(post.getPassword());
        post.setPassword(encoded);
        User user = mapper.map(post,User.class);
        user.trim();
        User save = repository.saveAndFlush(user);
        repository.refresh(save);
        return getResponse(save);
    }

    public void deleteUser(Integer id){
        User user = getUserById(id);
        repository.delete(user);
    }

    public UserResponseDTO updateUser(Integer id, UserEditDTO post){
//        TypeMap<UserEditDTO,User> map = mapper.typeMap(UserEditDTO.class,User.class);
//        map.setPropertyCondition(Conditions.isNotNull());
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

}
