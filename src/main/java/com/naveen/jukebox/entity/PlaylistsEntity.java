package com.naveen.jukebox.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "PLAYLISTS")
@Getter
@Setter
@NoArgsConstructor
public class PlaylistsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private List<String> songs;
    @ManyToOne()
    private long userId;

}
