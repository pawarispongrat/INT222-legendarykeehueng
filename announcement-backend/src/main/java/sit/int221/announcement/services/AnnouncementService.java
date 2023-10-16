package sit.int221.announcement.services;

import org.modelmapper.ModelMapper;
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
    private AnnouncementRepository repository;
    @Autowired
    private CategoryService categories;
    @Autowired
    private UserService users;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ListMapper listMapper;

    private boolean isEditor(List<String> authorities,Role... roles) {
        if (authorities == null) return false;
        List<String> matcher = Arrays.stream(roles).map(Enum::toString).toList();
        return authorities.stream().anyMatch(matcher::contains);
    }

    public List<? extends AnnouncementGuestResponse> getAnnouncement(Modes mode, List<String> authorities) {
        Pageable pageable = Pageable.unpaged();
        List<Announcement> announcement = getAnnouncementByAuthorities(pageable,mode,0,authorities);
//      announcement = getAnnouncementByMode(pageable,mode,0).getContent();
        Class<? extends AnnouncementGuestResponse> response = isEditor(authorities,Role.admin) ? AnnouncementAdminResponse.class : AnnouncementGuestResponse.class;
        List<? extends AnnouncementGuestResponse> responses = listMapper.mapList(announcement, response, mapper);
        Collections.reverse(responses);
        return responses;
    }

    private Announcement getAnnouncementById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException("announcementId"));
    }
    public AnnouncementAdminResponse getAdminAnnouncementById(Integer id) {
        return mapper.map(getAnnouncementById(id),AnnouncementAdminResponse.class);
    }
    public AnnouncementAdminResponse addAnnouncement(AnnouncementRequest post, String username){
        Announcement announcement = mapper.map(post,Announcement.class);
        User owner = users.getUserByUsername(username);
        Category category = categories.getCategoryById(post.getCategoryId());
        announcement.setAnnouncementOwner(owner);
        announcement.setCategory(category);
        Announcement saved = repository.saveAndFlush(announcement);
        return mapper.map(saved,AnnouncementAdminResponse.class);
    }

    public AnnouncementAdminResponse updateAnnouncement(Integer id, AnnouncementRequest post){
        Announcement announcement = mapper.map(post,Announcement.class);
        Category category = categories.getCategoryById(post.getCategoryId());
        announcement.setId(id);
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
        List<Announcement> announcements = oldUser.getAnnouncements();
        announcements.forEach((announcement -> announcement.setAnnouncementOwner(newUser)));
        return repository.saveAllAndFlush(announcements);
    }

    public PageDTO<AnnouncementGuestResponse> getAnnouncementPage(int page, int size, Modes mode, int category){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));
        Page<Announcement> announcement = getAnnouncementByMode(pageable,mode,category);
        return listMapper.toPageDTO(announcement, AnnouncementGuestResponse.class,mapper);
    }

    public List<Announcement> getAnnouncementByAuthorities(Pageable pageable, Modes mode, int category, List<String> authorities) {
        List<Announcement> announcements = getAnnouncementByMode(pageable,mode,category).getContent();
        if (authorities == null || authorities.contains(Role.admin.toString())) return announcements;
        return announcements.stream().filter((announcement ->
                authorities.contains(announcement.getAnnouncementOwner().getRole().toString()))).toList();
    }

    public Page<Announcement> getAnnouncementByMode(Pageable pageable, Modes mode, int category) {
        Page<Announcement> announcement = new PageImpl<>(new ArrayList<>(),pageable,0);
        Display show = Display.Y;
        switch (mode) {
            case admin -> announcement = repository.findAll(pageable);
            case active -> announcement = repository.findActive(show,category, ZonedDateTime.now(), pageable);
            case close -> announcement = repository.findClose(show,category, ZonedDateTime.now(),pageable);
        }
        return announcement;
    }
    public AnnouncementAdminResponse addView(Integer id) {
        Announcement announcement = getAnnouncementById(id);
        announcement.setViewCount(announcement.getViewCount()+1);
        repository.saveAndFlush(announcement);
        return mapper.map(announcement,AnnouncementAdminResponse.class);
    }
}
