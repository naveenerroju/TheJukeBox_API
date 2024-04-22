package com.naveen.jukebox.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "SONGS")
@JsonIgnoreProperties
public class SongsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "TITLE", nullable = false)
    @Size(min = 4, max = 20, message = "The title must be between 4 and 20 characters")
    private String title;
    @Column(name = "OWNER", nullable = false)
    @Size(min = 4, max = 20, message = "The OWNER must be between 4 and 20 characters")
    private String owner;
    @Column(name = "COLLABORATORS")
    private List<String> collaborators;
}
