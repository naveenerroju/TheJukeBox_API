package com.naveen.jukebox.service;

import com.naveen.jukebox.entity.SongsEntity;
import com.naveen.jukebox.exceptions.IncorrectInputsException;
import com.naveen.jukebox.model.SongsRequest;
import com.naveen.jukebox.model.SongsResponse;
import com.naveen.jukebox.repository.SongsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Songs.
 */
@Service
public class SongsService implements ISongsService {

    private final SongsRepository repository;
    private final ModelMapper mapper;

    /**
     * Constructor for SongsService.
     *
     * @param repository SongsRepository instance to interact with the songs database.
     * @param mapper     ModelMapper instance for mapping between DTOs and entities.
     */
    @Autowired
    public SongsService(SongsRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Adds a new song to the database.
     *
     * @param request SongsRequest object containing details of the song to be added.
     * @return SongsResponse object containing details of the added song.
     */
    @Override
    public SongsResponse addSong(SongsRequest request) {
        // Map SongsRequest to SongsEntity
        SongsEntity entity = mapRequestToEntity(request);

        // Save the entity in the repository
        SongsEntity savedEntity = repository.save(entity);

        // Map the saved entity back to SongsResponse
        return mapEntityToResponse(savedEntity);
    }


    /**
     * Deletes a song from the database.
     *
     * @param songId ID of the song to be deleted.
     */
    @Override
    public void deleteSong(long songId) {
        repository.deleteById(songId);
    }

    /**
     * Retrieves a song from the database by its ID.
     *
     * @param songId ID of the song to be retrieved.
     * @return SongsEntity object representing the song.
     * @throws IncorrectInputsException if the song with the given ID is not found.
     */
    protected SongsEntity getSongById(long songId) {
        Optional<SongsEntity> song = repository.findById(songId);
        if (song.isPresent()) {
            return song.get();
        } else {
            throw new IncorrectInputsException("Song id " + songId + " not found");
        }
    }


    /**
     * Retrieves all songs from the database.
     *
     * @return List of SongsResponse objects representing all songs in the database.
     */
    @Override
    public List<SongsResponse> getSongs() {

        Iterable<SongsEntity> entities = repository.findAll();
        List<SongsResponse> responses = new ArrayList<>();

        for (SongsEntity entity : entities) {
            SongsResponse response = mapEntityToResponse(entity);
            responses.add(response);
        }

        return responses;
    }

    /**
     * Adds multiple songs to the database.
     *
     * @param requests List of SongsRequest objects containing song details to be added.
     * @return List of SongsResponse objects representing the added songs.
     */
    public List<SongsResponse> addMultipleSongs(List<SongsRequest> requests) {
        List<SongsResponse> responses = new ArrayList<>();

        // Iterate over the list of SongsRequest objects
        for (SongsRequest request : requests) {
            // Add each song using the addSong method and collect the response
            responses.add(addSong(request));
        }

        return responses;
    }


    /**
     * Maps a SongsRequest object to a SongsEntity object.
     *
     * @param request SongsRequest object to be mapped.
     * @return SongsEntity object mapped from the SongsRequest object.
     */
    private SongsEntity mapRequestToEntity(SongsRequest request) {
        return this.mapper.map(request, SongsEntity.class);
    }

    /**
     * Maps a SongsEntity object to a SongsResponse object.
     *
     * @param entity SongsEntity object to be mapped.
     * @return SongsResponse object mapped from the SongsEntity object.
     */
    private SongsResponse mapEntityToResponse(SongsEntity entity) {
        return this.mapper.map(entity, SongsResponse.class);
    }
}
