package com.task.bookmark.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "bookmarks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark implements Serializable {

    @Id
    private Key bookmarkKey;

    private String title;

    private String url;

    @JsonIgnore
    private Folder folder;

    @JsonIgnore
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bookmark)) return false;
        Bookmark bookmark = (Bookmark) o;
        return getUrl().equals(bookmark.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getUser().getId());
    }
}
