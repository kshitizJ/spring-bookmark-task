package com.task.bookmark.services.implementation;

import com.google.api.client.util.Lists;
import com.task.bookmark.exceptions.UserExistException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.model.UserPrinciple;
import com.task.bookmark.repository.UserRepository;
import com.task.bookmark.services.FolderService;
import com.task.bookmark.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FolderService folderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Invalid email: " + email);
        else
            return new UserPrinciple(user);
    }

    @Override
    public User getCurrentLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with id does not exist."));
    }

    @Override
    public User addNewUser(String firstName, String lastName, String email, String password) {
        validateUser(StringUtils.EMPTY, email);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        List<Bookmark> bookmarks = new ArrayList<>();
        user.setBookmarks(bookmarks);
        List<Folder> folders = new ArrayList<>();
        user.setFolders(folders);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, String firstName, String lastName, String email) {
        User user = getUser(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = getUser(userId);
        List<Folder> folders = getFoldersByUserId(userId);
        folders.stream().map(folder -> {
            folderService.deleteFolder(folder.getFolderKey().getId());
            return folder;
        });
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    public List<Folder> getFoldersByUserId(Long id) {
        User user = getUser(id);
        return user.getFolders();
    }

    private User validateUser(String currentUserEmail, String newUserEmail) {
        User userByNewEmail = findUserByEmail(newUserEmail);
        if (StringUtils.isNotBlank(currentUserEmail)) {
            User currentUser = findUserByEmail(currentUserEmail);
            if (currentUser == null)
                throw new UsernameNotFoundException("No user found by this email.");
            if (userByNewEmail != null && !currentUser.getEmail().equals(userByNewEmail.getEmail()))
                throw new UserExistException("User with this email already exist.");
            return currentUser;
        } else {
            if (userByNewEmail != null)
                throw new UserExistException("User with this email already exist.");
            return null;
        }
    }
}
