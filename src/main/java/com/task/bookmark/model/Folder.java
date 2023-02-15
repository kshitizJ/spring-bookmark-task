package com.task.bookmark.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "folders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @OneToMany(mappedBy = "folder", cascade = PERSIST)
    @JsonBackReference
    private List<Bookmark> bookmarks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-folder")
    private User user;

}
