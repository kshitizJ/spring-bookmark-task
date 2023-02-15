package com.task.bookmark.services;

import com.task.bookmark.PostgreSqlContainer;
import com.task.bookmark.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceTest extends PostgreSqlContainer {
    @Autowired
    private UserService userService;

    @Test
    public void createUserTest() {
//      Given
        User user = new User();
        user.setFirstName("Kshitiz");
        user.setLastName("Jain");
        user.setEmail("kshitiz@mail.com");
        user.setPassword("KshitizJ");
//        user.setFolders(new ArrayList<>());

//      When
        User newUser = userService.addNewUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());

//      Then
        assertEquals(user.getFirstName(), newUser.getFirstName());
        assertEquals(user.getLastName(), newUser.getLastName());
        assertEquals(user.getEmail(), newUser.getEmail());
    }

    @Test
    public void getUserTest() {
//      Given
        User user = new User();
        user.setFirstName("Kshitiz");
        user.setLastName("Jain");
        user.setEmail("kshitiz@mail.com");
        user.setPassword("KshitizJ");

//      When
        User newUser = userService.addNewUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        User getUser = userService.getUser(newUser.getId());

//      Then
        assertEquals(user.getFirstName(), getUser.getFirstName());
        assertEquals(user.getLastName(), getUser.getLastName());
        assertEquals(user.getEmail(), getUser.getEmail());

    }

    @Test
    public void updateUserTest() {
//      Given
        User user = new User();
        user.setFirstName("Kshitiz");
        user.setLastName("Jain");
        user.setEmail("kshitiz@mail.com");
        user.setPassword("KshitizJ");

//      When
        User newUser = userService.addNewUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        user.setFirstName("Shantanu");
        user.setEmail("shantanu@mail.com");

        User updatedUser = userService.updateUser(newUser.getId(), user.getFirstName(), user.getLastName(), user.getEmail());

//      Then
        assertEquals(user.getFirstName(), updatedUser.getFirstName());
        assertEquals(user.getLastName(), updatedUser.getLastName());
        assertEquals(user.getEmail(), updatedUser.getEmail());

    }

    @Test
    public void deleteUserTest(){
        User user = new User();
        user.setFirstName("Kshitiz");
        user.setLastName("Jain");
        user.setEmail("kshitiz@mail.com");
        user.setPassword("KshitizJ");

//      When
        User newUser = userService.addNewUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        userService.deleteUser(newUser.getId());

//      Then
        assertThrows(UsernameNotFoundException.class, () -> userService.getUser(newUser.getId()));
    }
}
