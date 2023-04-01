package com.task.bookmark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Version;

import java.io.Serializable;
import java.util.List;


@Entity(name = "folders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Folder implements Serializable {

    @Id
    private Key folderKey;

    private String name;

    private Integer bookmarkCounter = 0;

    @Version
    private Integer version;

    @Reference
    private List<Bookmark> bookmarks;

    @JsonIgnore
    private User user;

}
