package com.task.bookmark.services;

import com.task.bookmark.model.Bookmark;

import java.util.List;

public interface BookmarkService {

    List<Bookmark> getAllBookmars();

    Bookmark createBookmark(String title, String url);

    Bookmark getBookmark(Long id);

    Bookmark updateBookmark(Long id, String title, String url, Long folderId);

    void deleteBookmark(Long id);

    List<Bookmark> createAllBookmarks(List<Bookmark> bookmarks);

}
