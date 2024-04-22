package com.naveen.jukebox.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SongsRequest {
    private String title;
    private String owner;
    private List<String> collaborators;
}
