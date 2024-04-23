package com.naveen.jukebox.service;

import com.naveen.jukebox.model.PlayListRequest;
import com.naveen.jukebox.model.PlaylistResponse;

import java.util.List;

public interface IPlaylistService {
    void createPlaylist(String username, PlayListRequest request);
    void addSongToPlaylist(long playlistId, long songId);
    PlaylistResponse getPlaylist(long playlistId);
    List<PlaylistResponse> getPlaylistsOfUser(String username);
}
