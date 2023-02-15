package com.task.bookmark.services;

import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    User getUser(Integer userId);

    User addNewUser(String firstName, String lastName, String email, String password);

    User updateUser(Integer userId, String firstName, String lastName, String email);

    List<User> getAllUsers();

    List<Folder> getFoldersByUserId(Integer id);

    void deleteUser(Integer userId);
}
