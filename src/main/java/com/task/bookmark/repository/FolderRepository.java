package com.task.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.bookmark.model.Folder;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

}
