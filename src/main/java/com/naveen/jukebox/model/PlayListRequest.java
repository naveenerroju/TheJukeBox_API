package com.naveen.jukebox.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayListRequest {
    private String title;
    private List<Long> songs;
}
