package com.task.bookmark.services.implementation;

import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.repository.FolderRepository;
import com.task.bookmark.repository.UserRepository;
import com.task.bookmark.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    @Override
    public Folder createFolder(String name, Integer dtoUserId) {
        Folder folder = new Folder();
        User user = Optional.ofNullable(dtoUserId).map(userId -> userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with given id does not exist."))).orElse(null);
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

    public List<Bookmark> getBookmarksByFolderId(Integer id) throws FolderNotFoundException {
        Folder folder = getFolder(id);
        return folder.getBookmarks();
    }

}
