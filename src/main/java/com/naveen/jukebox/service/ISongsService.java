package com.naveen.jukebox.service;

import com.naveen.jukebox.model.SongsRequest;
import com.naveen.jukebox.model.SongsResponse;

import java.util.List;

public interface ISongsService {
    SongsResponse addSong(SongsRequest request);

    List<SongsResponse> getSongs();
}
