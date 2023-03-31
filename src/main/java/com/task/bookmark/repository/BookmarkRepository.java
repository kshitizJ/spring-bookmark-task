package com.task.bookmark.repository;

import com.task.bookmark.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    Bookmark findBookmarkByUrlAndUserId(String url, Integer userId);

    List<Bookmark> findBookmarksByUserId(Integer userId);

    List<Bookmark> findBookmarksByFolderId(Integer folderId);

}
