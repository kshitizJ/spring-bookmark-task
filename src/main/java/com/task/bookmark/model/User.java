package com.task.bookmark.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @Reference
    private List<Folder> folders;

    @Reference
    private List<Bookmark> bookmarks;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
