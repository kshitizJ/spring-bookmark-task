package com.task.bookmark.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.task.bookmark.model.User;

public interface UserRepository extends DatastoreRepository<User, Long> {
    User findUserByEmail(String email);
}
