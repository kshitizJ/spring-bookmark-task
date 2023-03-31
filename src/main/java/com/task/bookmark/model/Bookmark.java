package com.task.bookmark.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "bookmarks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be empty.")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "URL cannot be empty.")
    private String url;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonBackReference
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-bookmark")
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
