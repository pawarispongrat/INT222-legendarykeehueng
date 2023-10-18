package sit.int221.announcement.services;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.response.announcement.AnnouncementAdminResponse;
import sit.int221.announcement.dtos.response.announcement.AnnouncementGuestResponse;
import sit.int221.announcement.dtos.request.AnnouncementRequest;
import sit.int221.announcement.dtos.PageDTO;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.models.Announcement;
import sit.int221.announcement.models.Category;
import sit.int221.announcement.models.User;
import sit.int221.announcement.repositories.AnnouncementRepository;
import sit.int221.announcement.utils.components.UserComponent;
import sit.int221.announcement.utils.enums.Display;
import sit.int221.announcement.utils.ListMapper;
import sit.int221.announcement.utils.enums.Modes;
import sit.int221.announcement.utils.enums.Role;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private UserComponent component;

    @Autowired
    private AnnouncementRepository repository;
    @Autowired
    private CategoryService categories;
    @Autowired
    private UserService users;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ListMapper listMapper;

    public List<? extends AnnouncementGuestResponse> getAnnouncement(Modes mode) {
        Pageable pageable = Pageable.unpaged();
        List<Announcement> announcement = getAnnouncementByMode(pageable,mode,0).getContent();
        Class<? extends AnnouncementGuestResponse> response = component.isEditor(Role.admin,Role.announcer) ? AnnouncementAdminResponse.class : AnnouncementGuestResponse.class;
        List<? extends AnnouncementGuestResponse> responses = listMapper.mapList(announcement, response, mapper);
        Collections.reverse(responses);
        return responses;
    }

    private Announcement getAnnouncementById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException("announcementId"));
    }

    @SuppressWarnings("unchecked")
    public <T extends AnnouncementGuestResponse> T getAdminAnnouncementById(Integer id, boolean count) {
        Class<? extends AnnouncementGuestResponse> response = component.isEditor(Role.admin,Role.announcer) ? AnnouncementAdminResponse.class : AnnouncementGuestResponse.class;
        if (count) return addView(id,response);
        else return (T) mapper.map(getAnnouncementById(id),response);
    }
    public AnnouncementAdminResponse addAnnouncement(AnnouncementRequest post){
        String username = component.getSubject();
        if (username == null) throw new ItemNotFoundException("username","Username not found in token");
        Announcement announcement = mapper.map(post,Announcement.class);
        User owner = users.getUserByUsername(username);
        Category category = categories.getCategoryById(post.getCategoryId());
        announcement.setId(null);
        announcement.setAnnouncementOwner(owner);
        announcement.setCategory(category);
        Announcement saved = repository.saveAndFlush(announcement);
        return mapper.map(saved,AnnouncementAdminResponse.class);
    }

    public AnnouncementAdminResponse updateAnnouncement(Integer id, AnnouncementRequest post){
        Announcement announcement = getAnnouncementById(id);
        announcement.setAnnouncementDisplay(post.getAnnouncementDisplay());
        announcement.setAnnouncementDescription(post.getAnnouncementDescription());
        announcement.setAnnouncementTitle(post.getAnnouncementTitle());
        announcement.setPublishDate(post.getPublishDate());
        announcement.setCloseDate(post.getCloseDate());
        announcement.setViewCount(post.getViewCount());
        Category category = categories.getCategoryById(post.getCategoryId());
        announcement.setCategory(category);

        Announcement saved = repository.saveAndFlush(announcement);
        return mapper.map(saved,AnnouncementAdminResponse.class);
    }

    public AnnouncementAdminResponse deleteAnnouncement(Integer id){
        Announcement announcement = getAnnouncementById(id);
        repository.delete(announcement);
        return mapper.map(announcement,AnnouncementAdminResponse.class);
    }


    public List<Announcement> updateAnnouncementOwnerByUserId(Integer ownerId, String newUsername){
        User oldUser = users.getUserById(ownerId);
        User newUser = users.getUserByUsername(newUsername);
        if (oldUser.equals(newUser)) return null;
        List<Announcement> announcements = oldUser.getAnnouncements();
        announcements.forEach((announcement -> announcement.setAnnouncementOwner(newUser)));
        return repository.saveAllAndFlush(announcements);
    }

    public PageDTO<? extends AnnouncementGuestResponse> getAnnouncementPage(int page, int size, Modes mode, int category){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));
        Page<Announcement> announcement = getAnnouncementByMode(pageable,mode,category);
        Class<? extends AnnouncementGuestResponse> responses = component.isEditor(Role.announcer,Role.admin) ? AnnouncementAdminResponse.class : AnnouncementGuestResponse.class;
        return listMapper.toPageDTO(announcement, responses,mapper);
    }

    public Page<Announcement> getAnnouncementByMode(Pageable pageable, Modes mode, int category) {
        Page<Announcement> announcement = new PageImpl<>(new ArrayList<>(),pageable,0);
        User user = !component.isEditor(Role.admin) ? users.getUserByUsernameOrNull(component.getSubject()) : null;
        Display show = Display.Y;
        switch (mode) {
            case admin -> announcement = repository.findAll(user, pageable);
            case active -> announcement = repository.findActive(show,category, ZonedDateTime.now(), user, pageable);
            case close -> announcement = repository.findClose(show,category, ZonedDateTime.now(), user,pageable);
        }
        return announcement;
    }
    @SuppressWarnings("unchecked")
    public <T extends AnnouncementGuestResponse> T addView(Integer id,Class<? extends AnnouncementGuestResponse> response) {
        Announcement announcement = getAnnouncementById(id);
        announcement.setViewCount(announcement.getViewCount()+1);
        repository.saveAndFlush(announcement);
        return (T) mapper.map(announcement,response);
    }
}
