package com.task.bookmark.resources;

import com.task.bookmark.dto.FolderBookmarkDTO;
import com.task.bookmark.exceptions.BookmarkNotFoundException;
import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.services.BookmarkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity<Bookmark> createBookmark(@RequestBody @Valid FolderBookmarkDTO bookmark) {
        Bookmark newBookmark = bookmarkService.createBookmark(bookmark.title(), bookmark.url());
        return new ResponseEntity<Bookmark>(newBookmark, CREATED);
    }

    @GetMapping("/{bookmarkId}")
    public ResponseEntity<Bookmark> getBookmark(@PathVariable Long bookmarkId) {
        Bookmark bookmark = bookmarkService.getBookmark(bookmarkId);
        return new ResponseEntity<Bookmark>(bookmark, OK);
    }

    @PutMapping("/{bookmarkId}")
    public ResponseEntity<Bookmark> updateBookmark(@PathVariable Long bookmarkId, @RequestBody @Valid FolderBookmarkDTO folderBookmarkDTO) {
        Bookmark updatedBookmark = bookmarkService.updateBookmark(bookmarkId, folderBookmarkDTO.title(),
                folderBookmarkDTO.url(), folderBookmarkDTO.folderId());
        return new ResponseEntity<Bookmark>(updatedBookmark, OK);
    }

    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long bookmarkId) throws BookmarkNotFoundException {
        bookmarkService.deleteBookmark(bookmarkId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Bookmark>> createAllBookmarks(@RequestBody @Valid List<FolderBookmarkDTO> folderBookmarkDTOs) {
        List<Bookmark> bookmarks = folderBookmarkDTOs.stream().map(folderBookmarkDTO -> {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setTitle(folderBookmarkDTO.title());
                    bookmark.setUrl(folderBookmarkDTO.url());
                    return bookmark;
                }
        ).collect(Collectors.toList());
        return new ResponseEntity<>(bookmarkService.createAllBookmarks(bookmarks), CREATED);
    }

}
