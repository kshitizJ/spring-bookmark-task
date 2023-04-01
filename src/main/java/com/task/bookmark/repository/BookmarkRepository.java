package com.task.bookmark.repository;

import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;

import java.util.List;

public interface BookmarkRepository extends DatastoreRepository<Bookmark, Long> {

    Bookmark findBookmarkByUrlAndUserId(String url, Long userId);

    List<Bookmark> findBookmarksByUser(User user);

    List<Bookmark> findBookmarksByFolder(Folder folder);

}
