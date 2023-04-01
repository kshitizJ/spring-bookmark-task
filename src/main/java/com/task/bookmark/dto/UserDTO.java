package com.task.bookmark.dto;

public record UserDTO(Long userId, String firstName, String lastName, String email, String password, Long folderId) {
}
