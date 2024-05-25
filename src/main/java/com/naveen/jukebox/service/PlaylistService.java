package com.naveen.jukebox.service;

import com.naveen.jukebox.entity.PlaylistsEntity;
import com.naveen.jukebox.entity.SongsEntity;
import com.naveen.jukebox.entity.UserEntity;
import com.naveen.jukebox.exceptions.IncorrectInputsException;
import com.naveen.jukebox.model.PlayListRequest;
import com.naveen.jukebox.model.PlaylistResponse;
import com.naveen.jukebox.repository.PlaylistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class that implements operations related to playlists.
 * Implements {@link IPlaylistService} interface.
 */
@Service
public class PlaylistService implements IPlaylistService {

    private final ModelMapper modelMapper;
    private final PlaylistRepository repository;
    private final UserService userService;
    private final SongsService songsService;

    /**
     * Constructor for PlaylistService.
     *
     * @param modelMapper  ModelMapper instance for mapping entities to DTOs and vice versa.
     * @param repository   PlaylistRepository instance for database operations.
     * @param userService  UserService instance for user-related operations.
     * @param songsService SongsService instance for song-related operations.
     */
    @Autowired
    public PlaylistService(ModelMapper modelMapper, PlaylistRepository repository, UserService userService, SongsService songsService) {
        this.modelMapper = modelMapper;
        this.repository = repository;
        this.userService = userService;
        this.songsService = songsService;
    }

    /**
     * Creates a new playlist for the specified user.
     *
     * @param username The username of the user for whom the playlist is created.
     * @param request  The request object containing playlist details and song IDs.
     * @throws IncorrectInputsException If the user does not exist or if any of the song IDs are invalid.
     */
    @Override
    public void createPlaylist(String username, PlayListRequest request) throws IncorrectInputsException {
        // Maps the PlayListRequest to PlaylistsEntity using ModelMapper
        PlaylistsEntity entity = this.modelMapper.map(request, PlaylistsEntity.class);

        // Sets the user for the playlist entity
        UserEntity user = userService.getUserWithUsername(username);
        if (user == null) {
            throw new IncorrectInputsException("User with username " + username + " does not exist");
        }
        entity.setUser(user);

        // Adds songs to the playlist entity
        for (Long songId : request.getSongs()) {
            SongsEntity song = songsService.getSongById(songId);
            if (song == null) {
                throw new IncorrectInputsException("Song with ID " + songId + " not found");
            }
            entity.getSongs().add(song);
        }

        // Saves the playlist entity to the repository
        repository.save(entity);
    }


    /**
     * Adds a song to the specified playlist.
     *
     * @param playlistId The ID of the playlist to which the song will be added.
     * @param songId     The ID of the song to be added.
     * @throws IncorrectInputsException If the playlist ID is invalid or if the song ID is invalid.
     */
    @Override
    public void addSongToPlaylist(long playlistId, long songId) throws IncorrectInputsException {
        // Retrieves the playlist entity by ID from the repository
        Optional<PlaylistsEntity> playlist = repository.findById(playlistId);

        // Retrieves the song entity by ID using the songsService
        SongsEntity song = songsService.getSongById(songId);

        // Checks if the playlist exists
        if (playlist.isPresent()) {
            // Adds the song to the playlist entity
            playlist.get().getSongs().add(song);
            // Saves the updated playlist entity back to the repository
            repository.save(playlist.get());
        } else {
            // Throws exception if the playlist ID is invalid
            throwInvalidPlaylistIdException(playlistId);
        }
    }


    /**
     * Retrieves a playlist by its ID.
     *
     * @param playlistId ID of the playlist to be retrieved.
     * @return PlaylistResponse containing the playlist details.
     * @throws IncorrectInputsException If the playlist ID is invalid.
     */
    @Override
    public PlaylistResponse getPlaylist(long playlistId) throws IncorrectInputsException {
        // Retrieves the playlist entity by ID from the repository
        Optional<PlaylistsEntity> playlist = repository.findById(playlistId);

        // Checks if the playlist exists
        if (playlist.isPresent()) {
            // Maps the playlist entity to PlaylistResponse using modelMapper
            return this.modelMapper.map(playlist.get(), PlaylistResponse.class);
        } else {
            // Throws exception if the playlist ID is invalid
            throwInvalidPlaylistIdException(playlistId);
            return null;
        }
    }


    /**
     * Retrieves all playlists belonging to a user.
     *
     * @param username Username of the user whose playlists are to be retrieved.
     * @return List of PlaylistResponse containing details of all playlists of the user.
     * @throws IncorrectInputsException If the username is invalid or user not found.
     */
    @Override
    public List<PlaylistResponse> getPlaylistsOfUser(String username) throws IncorrectInputsException {
        // Retrieves the UserEntity by username from the userService
        UserEntity user = userService.getUserWithUsername(username);

        // Retrieves all PlaylistsEntity belonging to the user from the repository
        List<PlaylistsEntity> playlists = repository.findByUser(user);

        // Maps each PlaylistsEntity to PlaylistResponse using modelMapper
        List<PlaylistResponse> response = new ArrayList<>();
        for (PlaylistsEntity entity : playlists) {
            response.add(this.modelMapper.map(entity, PlaylistResponse.class));
        }
        return response;
    }


    /**
     * Deletes a playlist by its ID.
     *
     * @param playlistId ID of the playlist to be deleted.
     * @throws IncorrectInputsException If the playlist ID is invalid or the playlist doesn't exist.
     */
    @Override
    public void deletePlaylist(long playlistId) throws IncorrectInputsException {
        // Checks if the playlist with the given ID exists in the repository
        if (repository.findById(playlistId).isEmpty()) {
            throwInvalidPlaylistIdException(playlistId);
        }

        // Deletes the playlist from the repository
        repository.deleteById(playlistId);
    }


    /**
     * Helper method to throw an exception for an invalid playlist ID.
     *
     * @param playlistId ID of the invalid playlist.
     * @throws IncorrectInputsException if the playlist with the given ID doesn't exist.
     */
    private void throwInvalidPlaylistIdException(long playlistId) {
        throw new IncorrectInputsException("Playlist with id " + playlistId + " doesn't exist");
    }
}
