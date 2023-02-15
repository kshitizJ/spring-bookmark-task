package com.task.bookmark.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.bookmark.dto.FolderBookmarkDTO;
import com.task.bookmark.exceptions.BookmarkNotFoundException;
import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.services.BookmarkService;

@RestController
@RequestMapping("api/v1/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping
    public ResponseEntity<List<Bookmark>> getAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkService.getAllBookmars();
        return new ResponseEntity<List<Bookmark>>(bookmarks, OK);
    }

    @PostMapping
    public ResponseEntity<Bookmark> createBookmark(@RequestBody FolderBookmarkDTO bookmark) {
        Bookmark newBookmark = bookmarkService.createBookmark(bookmark.title(), bookmark.url(), bookmark.userId());
        return new ResponseEntity<Bookmark>(newBookmark, CREATED);
    }

    @GetMapping("/{bookmarkId}")
    public ResponseEntity<Bookmark> getBookmark(@PathVariable Integer bookmarkId) throws BookmarkNotFoundException {
        Bookmark bookmark = bookmarkService.getBookmark(bookmarkId);
        return new ResponseEntity<Bookmark>(bookmark, OK);
    }

    @PutMapping("/{bookmarkId}")
    public ResponseEntity<Bookmark> updateBookmark(@PathVariable Integer bookmarkId,
                                                   @RequestBody FolderBookmarkDTO folderBookmarkDTO)
            throws BookmarkNotFoundException, FolderNotFoundException {
        Bookmark updatedBookmark = bookmarkService.updateBookmark(bookmarkId, folderBookmarkDTO.title(),
                folderBookmarkDTO.url(), folderBookmarkDTO.folderId());
        return new ResponseEntity<Bookmark>(updatedBookmark, OK);
    }

    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Integer bookmarkId) throws BookmarkNotFoundException {
        bookmarkService.deleteBookmark(bookmarkId);
        return ResponseEntity.ok().build();
    }

}
