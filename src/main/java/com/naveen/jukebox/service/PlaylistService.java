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

@Service
public class PlaylistService implements IPlaylistService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PlaylistRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private SongsService songsService;

    @Override
    public void createPlaylist(String username, PlayListRequest request) {
        PlaylistsEntity entity = this.modelMapper.map(request, PlaylistsEntity.class);
        entity.setUser(userService.getUserWithUsername(username));
        for(Long songId : request.getSongs()){
            entity.getSongs().add(songsService.getSongById(songId));
        }
        repository.save(entity);
    }

    @Override
    public void addSongToPlaylist(long playlistId, long songId) {
        Optional<PlaylistsEntity> playlist = repository.findById(playlistId);
        SongsEntity song = songsService.getSongById(songId);
        if(playlist.isPresent()) {
            playlist.get().getSongs().add(song);
            repository.save(playlist.get());
        } else {
        throw new IncorrectInputsException("Playlist with id " + playlistId+ " does not exist");
        }
    }

    @Override
    public PlaylistResponse getPlaylist(long playlistId) {
        Optional<PlaylistsEntity> playlist = repository.findById(playlistId);
        if(playlist.isPresent()) {
            return this.modelMapper.map(playlist.get(), PlaylistResponse.class);
        } else {
            throw new IncorrectInputsException("Playlist with id " + playlistId+ " does not exist");
        }
    }

    @Override
    public List<PlaylistResponse> getPlaylistsOfUser(String username) {
        UserEntity user = userService.getUserWithUsername(username);
        List<PlaylistsEntity> playlists = repository.findByUser(user);
        List<PlaylistResponse> response = new ArrayList<>();
        for(PlaylistsEntity entity: playlists) {
            response.add(this.modelMapper.map(entity, PlaylistResponse.class));
        }
        return response;
    }
}
