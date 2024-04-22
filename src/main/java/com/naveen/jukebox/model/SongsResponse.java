package com.naveen.jukebox.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SongsResponse {
    private long id;
    private String title;
    private String owner;
    private List<String> collaborators;
}
