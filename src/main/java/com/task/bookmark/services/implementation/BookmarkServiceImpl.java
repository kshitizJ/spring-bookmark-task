package com.task.bookmark.services.implementation;

import com.task.bookmark.exceptions.BookmarkNotFoundException;
import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.repository.BookmarkRepository;
import com.task.bookmark.repository.FolderRepository;
import com.task.bookmark.repository.UserRepository;
import com.task.bookmark.services.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkServiceImpl implements BookmarkService {

  @Autowired
  private BookmarkRepository bookmarkRepository;

  @Autowired
  private FolderRepository folderRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<Bookmark> getAllBookmars() {
    return bookmarkRepository.findAll();
  }

  @Override
  public Bookmark createBookmark(String title, String url, Integer dtoUserId) {
    Bookmark bookmark = new Bookmark();
    User user = Optional.ofNullable(dtoUserId).map(userId -> userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with given id does not exist."))).orElse(null);
    bookmark.setTitle(title);
    bookmark.setUrl(url);
    bookmark.setUser(user);
    Bookmark newBookmark = bookmarkRepository.save(bookmark);
    return newBookmark;
  }

  @Override
  public Bookmark getBookmark(Integer id) {
    Bookmark bookmark = bookmarkRepository.findById(id)
        .orElseThrow(() -> new BookmarkNotFoundException("Bookmark with the given id does not exist."));
    return bookmark;
  }

  @Override
  public Bookmark updateBookmark(Integer id, String title, String url, Integer dtoFolderId) {
    Bookmark bookmark = getBookmark(id);
    Folder folder = Optional.ofNullable(dtoFolderId).map(folderId -> folderRepository.findById(folderId)
        .orElseThrow(() -> new FolderNotFoundException("Folder with the given Id is not found."))).orElse(null);
    bookmark.setTitle(title);
    bookmark.setUrl(url);
    bookmark.setFolder(folder);
    Bookmark updatedBookmark = bookmarkRepository.save(bookmark);
    return updatedBookmark;
  }

  @Override
  public void deleteBookmark(Integer id) {
    Bookmark bookmark = getBookmark(id);
    bookmarkRepository.delete(bookmark);
  }

}
