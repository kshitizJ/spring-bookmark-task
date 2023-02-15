package com.task.bookmark.resources;

import com.task.bookmark.dto.JwtTokenDTO;
import com.task.bookmark.model.User;
import com.task.bookmark.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody User user) {
        String token = authService.authenticate(user.getEmail(), user.getPassword());
        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO(token);
        return new ResponseEntity<>(jwtTokenDTO, OK);
    }

    @PostMapping("register")
    public ResponseEntity<JwtTokenDTO> register(@RequestBody User user) {
        String token = authService.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO(token);
        return new ResponseEntity<>(jwtTokenDTO, CREATED);
    }

}
