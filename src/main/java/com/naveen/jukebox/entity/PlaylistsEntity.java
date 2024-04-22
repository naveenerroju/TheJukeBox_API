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
    @ManyToMany
    @JoinTable(
            name = "PLAYLIST_SONGS",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<SongsEntity> songs;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private long userId;

}
