package com.naveen.jukebox.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/playlists")
public class PlaylistsController {

    @PostMapping(path = "/create")
    public void createPlaylist(String playlist){

    }

    @PostMapping(path = "/add-song/playlist-id")
    public void addSongToPlaylist(@RequestParam(name = "playlist-id") long playlistId, long songId){

    }
}
