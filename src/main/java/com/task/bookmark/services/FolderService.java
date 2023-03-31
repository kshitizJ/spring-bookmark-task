package com.task.bookmark.services;

import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;

import java.util.List;

public interface FolderService {

    Folder getFolder(Integer id);

    Folder createFolder(String name);

    Folder updateFolder(Integer id, String name);

    void deleteFolder(Integer id);

    List<Bookmark> getBookmarksByFolderId(Integer id) throws FolderNotFoundException;

    List<Folder> getAllFolders();

}
