package com.naveen.jukebox.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "SONGS")
public class SongsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String owner;
    private List<String> collaborators;
}
