package com.task.bookmark.services;

import com.task.bookmark.PostgreSqlContainer;
import com.task.bookmark.exceptions.BookmarkNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.model.User;
import com.task.bookmark.services.implementation.BookmarkServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookmarkServiceTest extends PostgreSqlContainer {

    @Autowired
    private BookmarkServiceImpl bookmarkService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;

    @Test
    public void createBookmarkTest() {
        // Given
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle("New Title");
        bookmark.setUrl("new-title");

        // When
        Bookmark newBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl());

        // Then
        assertEquals(bookmark.getTitle(), newBookmark.getTitle());
        assertEquals(bookmark.getUrl(), newBookmark.getUrl());
    }

    @Test
    public void getBookmarkTest() {
        // Given
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle("New Title");
        bookmark.setUrl("new-title");

        // When
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl());
        Bookmark newBookmark = bookmarkService.getBookmark(createdBookmark.getId());

        // Then
        assertEquals(bookmark.getTitle(), newBookmark.getTitle());
    }

    @Test
    public void updateBookmarkTest() {
        // Given
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle("New Title");
        bookmark.setUrl("new-title");

        // When
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl());
        bookmark.setTitle("This is coding title");
        Bookmark newBookmark = bookmarkService.updateBookmark(createdBookmark.getId(), bookmark.getTitle(), bookmark.getUrl(), null);

        // Then
        assertEquals(bookmark.getTitle(), newBookmark.getTitle());
    }

    @Test
    public void deleteBookmarkTest() {
        // Given
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle("New Title");
        bookmark.setUrl("new-title");

        // When
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl());
        bookmarkService.deleteBookmark(createdBookmark.getId());

        // Then
        assertThrows(BookmarkNotFoundException.class, () -> bookmarkService.getBookmark(createdBookmark.getId()));
    }

    @Test
    public void bookmarkConcurrencyTest() throws InterruptedException {

        // Given
        User user = new User();
        user.setFirstName("Kshitiz");
        user.setLastName("Jain");
        user.setEmail("kshitiz@mail.com");
        user.setPassword("kshitiz");

        Folder folder = new Folder();
        folder.setName("coding");

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setTitle("First Bookmark");
        bookmark1.setUrl("first-bookmark");

        Bookmark bookmark2 = new Bookmark();
        bookmark2.setTitle("Second Bookmark");
        bookmark2.setUrl("second-bookmark");

        // When
        Runnable task1 = () -> {
            try {
                bookmarkService.updateBookmark(1, bookmark1.getTitle(), bookmark1.getUrl(), 1);
            } catch (ObjectOptimisticLockingFailureException e) {
                System.out.println("Task 1 failed");
                assertNotNull(e);
            }
        };

        Runnable task2 = () -> {
            try {
                bookmarkService.updateBookmark(2, bookmark2.getTitle(), bookmark2.getUrl(), 1);
            } catch (ObjectOptimisticLockingFailureException e) {
                System.out.println("Task 2 failed");
                assertNotNull(e);
            }
        };

        Thread thread1 = new Thread(() -> {
            userService.addNewUser("Kshitiz", "Jain", "kshitiz@mail.com", "kshitiz");
            folderService.createFolder(folder.getName());
            bookmarkService.createBookmark(bookmark1.getTitle(), bookmark1.getUrl());
            bookmarkService.createBookmark(bookmark2.getTitle(), bookmark2.getUrl());
        });

        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(task1);
        Thread thread3 = new Thread(task2);

        thread2.start();
        thread3.start();

        thread2.join();
        thread3.join();

        // Then
        assertEquals(1, folderService.getFolder(1).getBookmarkCounter());
    }

}
