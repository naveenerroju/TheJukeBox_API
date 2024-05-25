package com.naveen.jukebox.service;

import com.naveen.jukebox.model.SongsRequest;
import com.naveen.jukebox.model.SongsResponse;

import java.util.List;

/**
 * Interface defining operations related to songs in a Jukebox application.
 */
public interface ISongsService {

    /**
     * Adds a new song to the database.
     *
     * @param request SongsRequest object containing details of the song to be added.
     * @return SongsResponse containing details of the added song.
     */
    SongsResponse addSong(SongsRequest request);

    /**
     * Deletes a song from the database.
     *
     * @param songId ID of the song to be deleted.
     */
    void deleteSong(long songId);

    /**
     * Retrieves all songs from the database.
     *
     * @return List of SongsResponse containing details of all songs.
     */
    List<SongsResponse> getSongs();
}
