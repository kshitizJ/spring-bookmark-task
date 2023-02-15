package com.task.bookmark.services;

import com.task.bookmark.PostgreSqlContainer;
import com.task.bookmark.exceptions.FolderNotFoundException;
import com.task.bookmark.model.Folder;
import com.task.bookmark.services.implementation.FolderServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class FolderServiceTest extends PostgreSqlContainer {

    @Autowired
    private FolderServiceImpl folderService;

    @Test
    public void createFolderTest() {
        // Given
        Folder folder = new Folder();
        // folder.setId(3);
        folder.setName("Coding");
        // folder.setBookmarks(new ArrayList<>());

        // When
        Folder newFolder = folderService.createFolder(folder.getName(), null);

        // Then
        assertEquals(folder.getName(), newFolder.getName());
    }

    @Test
    public void getFolderTest() {
        // Given
        Folder folder = new Folder();
        folder.setName("Coding");

        // When
        Folder createdFolder = folderService.createFolder(folder.getName(), null);
        Folder newFolder = folderService.getFolder(createdFolder.getId());

        // Then
        assertEquals(folder.getName(), newFolder.getName());
    }

    @Test
    public void updateFolderTest() {
        // Given
        Folder folder = new Folder();
        folder.setName("Coding");

        // When
        Folder createdFolder = folderService.createFolder(folder.getName(), null);

        folder.setName("Coding with Kshitiz");
        Folder updatedFolder = folderService.updateFolder(createdFolder.getId(), "Coding with Kshitiz");

        // Then
        assertEquals(folder.getName(), updatedFolder.getName());
    }

    @Test
    public void deleteFolderTest() {
        // Given
        Folder folder = new Folder();
        folder.setName("Coding");

        // When
        Folder createdFolder = folderService.createFolder(folder.getName(), null);
        folderService.deleteFolder(createdFolder.getId());

        // Then
        assertThrows(FolderNotFoundException.class, () -> folderService.getFolder(createdFolder.getId()));
    }

}
