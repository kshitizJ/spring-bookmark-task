package com.task.bookmark.services;

public interface AuthService {

    String authenticate(String email, String password);

    String registerUser(String firstName, String lastName, String email, String password);

}
