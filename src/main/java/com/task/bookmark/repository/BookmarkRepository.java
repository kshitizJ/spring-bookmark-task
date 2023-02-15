package com.task.bookmark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.bookmark.model.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
  List<Bookmark> findByFolderId(Long folderId);
}
