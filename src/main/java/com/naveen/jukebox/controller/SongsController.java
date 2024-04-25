package com.naveen.jukebox.controller;

import com.naveen.jukebox.model.SongsRequest;
import com.naveen.jukebox.model.SongsResponse;
import com.naveen.jukebox.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongsController {

    @Autowired
    private SongsService songsService;

    @PostMapping(path = "/add")
    public ResponseEntity<SongsResponse> addSongs(@RequestBody SongsRequest request) {
        SongsResponse response = songsService.addSong(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    @PostMapping(path = "/add-all")
    public ResponseEntity<List<SongsResponse>> addMultipleSongs(@RequestBody List<SongsRequest> request) {
        List<SongsResponse> response = songsService.addMultipleSongs(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<SongsResponse>> getAllSongs() {
        List<SongsResponse> responses = songsService.getSongs();
        return new ResponseEntity<>(responses, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping(path = "/delete/{songId}")
    public ResponseEntity<String> deleteSong(@PathVariable(name = "songId") long songId) {
        songsService.deleteSong(songId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
    }

}
