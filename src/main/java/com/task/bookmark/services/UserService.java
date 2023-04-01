package com.task.bookmark.services;

import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    User getCurrentLoggedInUser();

    User getUser(Long userId);

    User addNewUser(String firstName, String lastName, String email, String password);

    User updateUser(Long userId, String firstName, String lastName, String email);

    List<User> getAllUsers();

    List<Folder> getFoldersByUserId(Long id);

    void deleteUser(Long userId);
}
