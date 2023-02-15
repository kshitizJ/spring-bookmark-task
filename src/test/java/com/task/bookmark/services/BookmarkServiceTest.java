package com.task.bookmark.services;

import com.task.bookmark.PostgreSqlContainer;
import com.task.bookmark.exceptions.BookmarkNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.services.implementation.BookmarkServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class BookmarkServiceTest extends PostgreSqlContainer {

    @Autowired
    private BookmarkServiceImpl bookmarkService;

    @Test
    public void createBookmarkTest() {
        // Given
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle("New Title");
        bookmark.setUrl("new-title");

        // When
        Bookmark newBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl(), null);

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
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl(), null);
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
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl(), null);
        bookmark.setTitle("This is coding title");
        Bookmark newBookmark = bookmarkService.updateBookmark(createdBookmark.getId(), bookmark.getTitle(),
                bookmark.getUrl(), null);

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
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark.getTitle(), bookmark.getUrl(), null);
        bookmarkService.deleteBookmark(createdBookmark.getId());

        // Then
        assertThrows(BookmarkNotFoundException.class, () -> bookmarkService.getBookmark(createdBookmark.getId()));
    }

}
