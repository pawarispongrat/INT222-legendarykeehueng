package sit.int221.announcement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.AnnouncementRequestDTO;
import sit.int221.announcement.dtos.request.UserRequestDTO;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.models.Announcement;
import sit.int221.announcement.models.Category;
import sit.int221.announcement.models.User;
import sit.int221.announcement.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;

    public List<User> getUser() {
        return repository.findAll();
    }
    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException("User id not found"));
    }

    public User addUser(UserRequestDTO post){
        return updateUser(null,post);
    }

    public void deleteUser(Integer id){
        User user = getUserById(id);
        repository.delete(user);
    }

    public User updateUser(Integer id,UserRequestDTO post){
        User user = mapper.map(post,User.class);
        user.setId(id);
        return repository.saveAndFlush(user);
    }
}
