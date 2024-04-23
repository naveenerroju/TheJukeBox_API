package com.naveen.jukebox.controller;

import com.naveen.jukebox.model.PlayListRequest;
import com.naveen.jukebox.model.PlaylistResponse;
import com.naveen.jukebox.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/playlists")
public class PlaylistsController {

    @Autowired
    private PlaylistService service;

    @PostMapping(path = "/create/{username}")
    public ResponseEntity<String> createPlaylist(@PathVariable("username") String username, @RequestBody PlayListRequest playlist){
        service.createPlaylist(username, playlist);
        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }

    @PutMapping(path = "/add-song/{playlistId}/{songId}")
    public ResponseEntity<String> addSongToPlaylist(@PathVariable(name = "playlistId") long playlistId, @PathVariable(name = "songId") long songId){
        service.addSongToPlaylist(playlistId, songId);
        return new ResponseEntity<>("Successfully added", HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/get/{playlistId}")
    public ResponseEntity<PlaylistResponse> getPlayList(@PathVariable(name = "playlistId") long playlistId){
        PlaylistResponse response = service.getPlaylist(playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/get/search")
    public ResponseEntity<List<PlaylistResponse>> getPlayListOfUser(@RequestParam(required = true) String username){
        List<PlaylistResponse> response = service.getPlaylistsOfUser(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
