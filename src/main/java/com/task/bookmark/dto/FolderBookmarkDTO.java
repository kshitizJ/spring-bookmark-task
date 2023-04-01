package com.task.bookmark.dto;

import jakarta.validation.constraints.NotBlank;

public record FolderBookmarkDTO(
        Long bookmarkId,
        @NotBlank(message = "Title cannot be empty") String title,
        @NotBlank(message = "Url cannot be empty") String url,
        Long folderId
) {
}
