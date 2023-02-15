package com.task.bookmark.resources;

import com.task.bookmark.dto.UserDTO;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.addNewUser(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.password());
        return new ResponseEntity<>(user, CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId) {
        User user = userService.getUser(userId);
        return new ResponseEntity<>(user, OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(userId, userDTO.firstName(), userDTO.lastName(), userDTO.email());
        return new ResponseEntity<>(user, OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{userId}/folders")
    public ResponseEntity<List<Folder>> getFoldersByUser(@PathVariable Integer userId){
        List<Folder> foldersByUser = userService.getFoldersByUserId(userId);
        return new ResponseEntity<>(foldersByUser, OK);
    }

}
