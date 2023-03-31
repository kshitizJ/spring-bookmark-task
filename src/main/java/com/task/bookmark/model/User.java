package com.task.bookmark.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "Email cannot be empty.")
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user", cascade = PERSIST)
    @JsonManagedReference("user-folder")
    private List<Folder> folders;

    @OneToMany(mappedBy = "user", cascade = PERSIST)
    @JsonManagedReference("user-bookmark")
    private List<Bookmark> bookmarks;

}
