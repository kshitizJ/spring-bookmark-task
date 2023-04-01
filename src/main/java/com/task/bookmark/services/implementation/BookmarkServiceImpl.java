package com.task.bookmark.services.implementation;

import com.google.api.client.util.Lists;
import com.task.bookmark.exceptions.BookmarkExistException;
import com.task.bookmark.exceptions.BookmarkNotFoundException;
import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.repository.BookmarkRepository;
import com.task.bookmark.repository.FolderRepository;
import com.task.bookmark.repository.UserRepository;
import com.task.bookmark.services.BookmarkService;
import com.task.bookmark.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    @Cacheable(value = "bookmarks", key = "@userServiceImpl.getCurrentLoggedInUser().getId()")
    public List<Bookmark> getAllBookmars() {
        User user = userService.getCurrentLoggedInUser();
        return bookmarkRepository.findBookmarksByUser(user);
    }

    @Override
    @CacheEvict(value = "bookmarks", key = "@userServiceImpl.getCurrentLoggedInUser().getId()")
    public Bookmark createBookmark(String title, String url) {
        Bookmark bookmark = new Bookmark();

        User user = userService.getCurrentLoggedInUser();
        Bookmark existBookmark = bookmarkRepository.findBookmarkByUrlAndUserId(url, user.getId());

        if (existBookmark != null)
            throw new BookmarkExistException("Bookmark already exist");

        bookmark.setTitle(title);
        bookmark.setUrl(url);
        bookmark.setUser(user);
        Bookmark newBookmark = bookmarkRepository.save(bookmark);
        List<Bookmark> bookmarks = new ArrayList<>(user.getBookmarks());
        bookmarks.add(newBookmark);
        user.setBookmarks(bookmarks);
        userRepository.save(user);
        return newBookmark;
    }

    @Override
    @Cacheable(value = "bookmark", key = "#id")
    public Bookmark getBookmark(Long id) {
        return bookmarkRepository.findById(id)
                .orElseThrow(() -> new BookmarkNotFoundException("Bookmark with the given id does not exist."));
    }

    @Override
    @CacheEvict(value = "bookmarks", key = "@userServiceImpl.getCurrentLoggedInUser().getId()")
    @CachePut(value = "bookmark", key = "#id")
    public Bookmark updateBookmark(Long id, String title, String url, Long newFolderId) {
        Bookmark bookmark = getBookmark(id);

        Folder folder = Optional.ofNullable(newFolderId).map(folderId -> folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder with the given ID does not exist."))).orElse(null);

        if (bookmark.getFolder() == null) {

            folder.setBookmarkCounter(folder.getBookmarkCounter() + 1);
            List<Bookmark> bookmarks = new ArrayList<>(folder.getBookmarks());
            bookmarks.add(bookmark);
            folder.setBookmarks(bookmarks);
            folderRepository.save(folder);

        } else {

            if (bookmark.getFolder().getFolderKey().getId() != newFolderId) {
                Folder prevFolder = bookmark.getFolder();
                List<Bookmark> bookmarks = new ArrayList<>(prevFolder.getBookmarks());
                bookmarks.remove(bookmark);
                prevFolder.setBookmarks(bookmarks);
                prevFolder.setBookmarkCounter(prevFolder.getBookmarkCounter() - 1);
                folderRepository.save(prevFolder);
                bookmarks = new ArrayList<>(folder.getBookmarks());
                bookmarks.add(bookmark);
                folder.setBookmarks(bookmarks);
                folder.setBookmarkCounter(folder.getBookmarkCounter() + 1);
                folderRepository.save(folder);
            }

        }

        bookmark.setTitle(title);
        bookmark.setUrl(url);
        bookmark.setFolder(folder);
        Bookmark updatedBookmark = bookmarkRepository.save(bookmark);
        return updatedBookmark;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "bookmarks", key = "@userServiceImpl.getCurrentLoggedInUser().getId()"),
            @CacheEvict(value = "bookmark", key = "#id")
    })
    public void deleteBookmark(Long id) {
        Bookmark bookmark = getBookmark(id);
        Folder folder = Optional.ofNullable(bookmark.getFolder()).map(curFolder -> folderRepository.findById(curFolder.getFolderKey().getId()).orElseThrow(() -> new FolderNotFoundException("Folder with the given Id is not found."))).orElse(null);

        Optional.ofNullable(folder).ifPresent(curFolder -> {
            List<Bookmark> bookmarks = new ArrayList<>(curFolder.getBookmarks());
            bookmarks.remove(bookmark);
            curFolder.setBookmarks(bookmarks);
            curFolder.setBookmarkCounter(curFolder.getBookmarkCounter() - 1);
            folderRepository.save(curFolder);
        });
        bookmarkRepository.delete(bookmark);
    }

    @Transactional
    @Override
    @CacheEvict(value = "bookmarks", key = "@userServiceImpl.getCurrentLoggedInUser().getId()")
    public List<Bookmark> createAllBookmarks(List<Bookmark> bookmarks) {
        User user = userService.getCurrentLoggedInUser();
        List<Bookmark> userBookmarks = new ArrayList<>(user.getBookmarks());
        List<Bookmark> bookmarkList = bookmarks.stream().map(bookmark -> {
            bookmark.setUser(user);
            userBookmarks.add(bookmark);
            return bookmark;
        }).collect(Collectors.toList());
        user.setBookmarks(userBookmarks);
        List<Bookmark> newBookmarks = Lists.newArrayList(bookmarkRepository.saveAll(bookmarkList));
        return newBookmarks;
    }

}