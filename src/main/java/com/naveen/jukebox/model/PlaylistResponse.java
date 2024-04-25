package com.naveen.jukebox.model;

import com.naveen.jukebox.entity.SongsEntity;
import com.naveen.jukebox.entity.UserEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaylistResponse {
    private Long id;
    private String title;
    private List<SongsEntity> songs;
    private UserEntity user;
}
