package com.naveen.jukebox.service;

import com.naveen.jukebox.model.PlayListRequest;

public interface IPlaylistService {
    void createPlaylist(String username, PlayListRequest request);
    void addSongToPlaylist(long playlistId, long songId);
}
