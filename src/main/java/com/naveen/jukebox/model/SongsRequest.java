package com.naveen.jukebox.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SongsRequest {
    @Schema(description = "Title of the song", example = "My Song")
    private String title;
    @Schema(description = "Owner of the song", example = "John Doe")
    private String owner;
    @Schema(description = "List of collaborators", example = "[\"Jane Doe\", \"Alice Smith\"]")
    private List<String> collaborators;
}
