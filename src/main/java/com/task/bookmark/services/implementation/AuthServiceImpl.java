package com.task.bookmark.services.implementation;

import com.task.bookmark.model.User;
import com.task.bookmark.model.UserPrinciple;
import com.task.bookmark.services.AuthService;
import com.task.bookmark.services.UserService;
import com.task.bookmark.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Override
    public String authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userService.findUserByEmail(email);
        UserPrinciple userPrinciple = new UserPrinciple(user);
        String token = jwtTokenProvider.generateJwtToken(userPrinciple);
        return token;
    }

    @Override
    public String registerUser(String firstName, String lastName, String email, String password) {
        userService.addNewUser(firstName, lastName, email, password);
        return authenticate(email, password);
    }

}
