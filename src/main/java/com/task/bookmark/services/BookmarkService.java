package com.task.bookmark.services;

import com.task.bookmark.model.Bookmark;

import java.util.List;

public interface BookmarkService {

    List<Bookmark> getAllBookmars();

    Bookmark createBookmark(String title, String url);

    Bookmark getBookmark(Integer id);

    Bookmark updateBookmark(Integer id, String title, String url, Integer folderId);

    void deleteBookmark(Integer id);

    List<Bookmark> createAllBookmarks(List<Bookmark> bookmarks);

}
