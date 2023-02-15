package com.task.bookmark.resources;

import com.task.bookmark.dto.UserFolderDTO;
import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Bookmark;
import com.task.bookmark.model.Folder;
import com.task.bookmark.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @GetMapping
    public ResponseEntity<List<Folder>> getAllFolders() {
        List<Folder> folders = folderService.getAllFolders();
        return new ResponseEntity<List<Folder>>(folders, OK);
    }

    @PostMapping
    public ResponseEntity<Folder> createFolder(@RequestBody UserFolderDTO folder) {
        Folder newfolder = folderService.createFolder(folder.name(), folder.userId());
        return new ResponseEntity<Folder>(newfolder, CREATED);
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<Folder> getFolder(@PathVariable Integer folderId) throws FolderNotFoundException {
        Folder folder = folderService.getFolder(folderId);
        return new ResponseEntity<Folder>(folder, OK);
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<Folder> updateFolder(@PathVariable Integer folderId, @RequestBody UserFolderDTO folder)
            throws FolderNotFoundException {
        Folder updateFolder = folderService.updateFolder(folderId, folder.name());
        return new ResponseEntity<Folder>(updateFolder, OK);
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Integer folderId) throws FolderNotFoundException {
        folderService.deleteFolder(folderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{folderId}/bookmarks")
    public ResponseEntity<List<Bookmark>> getBookmarksByFolder(@PathVariable Integer folderId) {
        List<Bookmark> bookmarksByFolder = folderService.getBookmarksByFolderId(folderId);
        return new ResponseEntity<>(bookmarksByFolder, OK);
    }

}
