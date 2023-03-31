package com.task.bookmark.services.implementation;

import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.repository.BookmarkRepository;
import com.task.bookmark.repository.FolderRepository;
import com.task.bookmark.services.FolderService;
import com.task.bookmark.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Folder> getAllFolders() {
        User user = userService.getCurrentLoggedInUser();
        List<Folder> folders = folderRepository.findFoldersByUserId(user.getId());
        return folders;
    }

    @Override
    public Folder createFolder(String name) {
        Folder folder = new Folder();
        User user = userService.getCurrentLoggedInUser();
        folder.setName(name);
        List<Bookmark> bookmarks = new ArrayList<>();
        folder.setBookmarks(bookmarks);
        folder.setUser(user);
        Folder newFolder = folderRepository.save(folder);
        return newFolder;
    }

    @Override
    public Folder getFolder(Integer id) throws FolderNotFoundException {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new FolderNotFoundException("Folder with the given id does not exist."));
        return folder;
    }

    @Override
    public Folder updateFolder(Integer id, String name) throws FolderNotFoundException {
        Folder folder = getFolder(id);
        folder.setName(name);
        Folder updatedFolder = folderRepository.save(folder);
        return updatedFolder;
    }

    @Override
    public void deleteFolder(Integer id) throws FolderNotFoundException {
        Folder folder = getFolder(id);
        folderRepository.delete(folder);
    }

    @Override
    public List<Bookmark> getBookmarksByFolderId(Integer id) throws FolderNotFoundException {
        List<Bookmark> bookmarks = bookmarkRepository.findBookmarksByFolderId(id);
        return bookmarks;
    }

}
