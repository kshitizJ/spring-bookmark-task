package com.task.bookmark.services.implementation;

import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.repository.BookmarkRepository;
import com.task.bookmark.repository.FolderRepository;
import com.task.bookmark.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Folder> getAllFolders() {
        User user = userService.getCurrentLoggedInUser();
        return folderRepository.findFoldersByUserId(user.getId());
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
        List<Folder> folders = new ArrayList<>(user.getFolders());
        folders.add(newFolder);
        user.setFolders(folders);
        userRepository.save(user);
        return newFolder;
    }

    @Override
    public Folder getFolder(Long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new FolderNotFoundException("Folder with the given id does not exist."));
    }

    @Override
    public Folder updateFolder(Long id, String name) {
        Folder folder = getFolder(id);
        folder.setName(name);
        return folderRepository.save(folder);
    }

    @Override
    public void deleteFolder(Long id) {
        User user = userService.getCurrentLoggedInUser();
        Folder folder = getFolder(id);
        List<Bookmark> bookmarks = getBookmarksByFolderId(id);
        bookmarks.stream().map(bookmark -> {
            bookmarkRepository.delete(bookmark);
            return bookmark;
        });
        List<Folder> folders = new ArrayList<>(user.getFolders());
        folders.remove(folder);
        user.setFolders(folders);
        folderRepository.delete(folder);
        userRepository.save(user);
    }

    @Override
    public List<Bookmark> getBookmarksByFolderId(Long id) {
        Folder folder = getFolder(id);
        return bookmarkRepository.findBookmarksByFolder(folder);
    }

}
