package com.task.bookmark.dto;

public record UserDTO(Integer userId, String firstName, String lastName, String email, String password, Integer folderId) {
}
