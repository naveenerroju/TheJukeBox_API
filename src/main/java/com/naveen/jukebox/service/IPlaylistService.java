package com.naveen.jukebox.service;

import com.naveen.jukebox.model.PlayListRequest;
import com.naveen.jukebox.model.PlaylistResponse;

import java.util.List;

/**
 * Interface defining operations related to playlists in a Jukebox application.
 */
public interface IPlaylistService {

    /**
     * Creates a new playlist for a user.
     *
     * @param username Username of the user creating the playlist.
     * @param request  Playlist request object containing playlist details.
     */
    void createPlaylist(String username, PlayListRequest request);

    /**
     * Adds a song to an existing playlist.
     *
     * @param playlistId ID of the playlist to which the song will be added.
     * @param songId     ID of the song to be added to the playlist.
     */
    void addSongToPlaylist(long playlistId, long songId);

    /**
     * Retrieves a playlist by its ID.
     *
     * @param playlistId ID of the playlist to be retrieved.
     * @return PlaylistResponse containing the playlist details.
     */
    PlaylistResponse getPlaylist(long playlistId);

    /**
     * Retrieves all playlists belonging to a user.
     *
     * @param username Username of the user whose playlists are to be retrieved.
     * @return List of PlaylistResponse containing details of all playlists of the user.
     */
    List<PlaylistResponse> getPlaylistsOfUser(String username);

    /**
     * Deletes a playlist by its ID.
     *
     * @param playlistId ID of the playlist to be deleted.
     */
    void deletePlaylist(long playlistId);
}
