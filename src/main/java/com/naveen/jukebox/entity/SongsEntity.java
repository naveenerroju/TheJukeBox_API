package com.naveen.jukebox.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "SONGS")
public class SongsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "TITLE", nullable = false)
    @Size(min = 4, max = 20, message = "The title must be between 4 and 20 characters")
    private String title;

    @NotBlank(message = "Owner cannot be blank")
    @Column(name = "OWNER", nullable = false)
    @Size(min = 4, max = 20, message = "The owner must be between 4 and 20 characters")
    private String owner;

    @Column(name = "COLLABORATORS")
    private List<String> collaborators;

    @ManyToMany(mappedBy = "songs")
    @JsonIgnore
    private List<PlaylistsEntity> playlists;
}