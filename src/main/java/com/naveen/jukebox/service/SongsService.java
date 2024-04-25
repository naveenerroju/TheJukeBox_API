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

@Service
public class SongsService implements ISongsService {
    @Autowired
    private SongsRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public SongsResponse addSong(SongsRequest request) {
        SongsEntity entity = mapRequestToEntity(request);
        SongsEntity response = repository.save(entity);
        return mapEntityToResponse(response);
    }

    @Override
    public void deleteSong(long songId) {
        repository.deleteById(songId);
    }

    protected SongsEntity getSongById(long songId){
        Optional<SongsEntity> song = repository.findById(songId);
        if(song.isPresent()){
            return song.get();
        } else {
            throw new IncorrectInputsException("Song id " + songId + " not found");
        }
    }

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

    public List<SongsResponse> addMultipleSongs(List<SongsRequest> requests) {
        List<SongsResponse> responses = new ArrayList<>();
        for (SongsRequest request : requests) {
            responses.add(addSong(request));
        }
        return responses;
    }


    private SongsEntity mapRequestToEntity(SongsRequest request) {
        return this.mapper.map(request, SongsEntity.class);
    }

    private SongsResponse mapEntityToResponse(SongsEntity entity) {
        return this.mapper.map(entity, SongsResponse.class);
    }
}
