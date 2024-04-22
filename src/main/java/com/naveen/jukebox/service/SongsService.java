package com.naveen.jukebox.service;

import com.naveen.jukebox.entity.SongsEntity;
import com.naveen.jukebox.model.SongsRequest;
import com.naveen.jukebox.model.SongsResponse;
import com.naveen.jukebox.repository.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongsService implements ISongsService {
    @Autowired
    private SongsRepository repository;

    @Override
    public SongsResponse addSong(SongsRequest request) {
        SongsEntity entity = mapRequestToEntity(request);
        SongsEntity response = repository.save(entity);
        return mapEntityToResponse(response);
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


    private SongsEntity mapRequestToEntity(SongsRequest request) {
        SongsEntity entity = new SongsEntity();
        entity.setOwner(request.getOwner());
        entity.setTitle(request.getTitle());
        entity.setCollaborators(request.getCollaborators());

        return entity;
    }

    private SongsResponse mapEntityToResponse(SongsEntity entity) {
        SongsResponse response = new SongsResponse();
        response.setTitle(entity.getTitle());
        response.setCollaborators(entity.getCollaborators());
        response.setId(entity.getId());
        response.setOwner(entity.getOwner());

        return response;
    }
}
