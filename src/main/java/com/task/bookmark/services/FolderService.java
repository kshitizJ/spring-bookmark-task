package com.task.bookmark.services;

import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;

import java.util.List;

public interface FolderService {

    Folder getFolder(Long id);

    Folder createFolder(String name);

    Folder updateFolder(Long id, String name);

    void deleteFolder(Long id);

    List<Bookmark> getBookmarksByFolderId(Long id);

    List<Folder> getAllFolders();

}
