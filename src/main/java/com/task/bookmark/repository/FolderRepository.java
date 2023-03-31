package com.task.bookmark.repository;

import com.task.bookmark.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Integer> {
    List<Folder> findFoldersByUserId(Integer id);
}
