package com.task.bookmark.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.task.bookmark.model.Folder;

import java.util.List;

public interface FolderRepository extends DatastoreRepository<Folder, Long> {
    List<Folder> findFoldersByUserId(Long id);
}
