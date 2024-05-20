package com.naveen.jukebox.controller;

import com.naveen.jukebox.model.PlayListRequest;
import com.naveen.jukebox.model.PlaylistResponse;
import com.naveen.jukebox.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Creates a playlist of songs for the given user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "User with username not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Song id songId not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})})
    @PostMapping(path = "/create/{username}")
    public ResponseEntity<String> createPlaylist(@PathVariable("username") String username, @RequestBody PlayListRequest playlist) {
        service.createPlaylist(username, playlist);
        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }

    @Operation(summary = "adds song to playlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "User with username not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Playlist with playlistId not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Song id songId not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})})
    @PutMapping(path = "/add-song/{playlistId}/{songId}")
    public ResponseEntity<String> addSongToPlaylist(@PathVariable(name = "playlistId") long playlistId, @PathVariable(name = "songId") long songId){
        service.addSongToPlaylist(playlistId, songId);
        return new ResponseEntity<>("Successfully added", HttpStatus.ACCEPTED);
    }

    @Operation(summary = "gets playlist of requested playlistId from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PlaylistResponse of the playlist id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Playlist with playlistId not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})})
    @GetMapping(path = "/get/{playlistId}")
    public ResponseEntity<PlaylistResponse> getPlayList(@PathVariable(name = "playlistId") long playlistId){
        PlaylistResponse response = service.getPlaylist(playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "gets all the playlists of requested user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of PlaylistResponses of the specified user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "User with username not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Playlist with playlistId not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})})
    @GetMapping(path = "/get/search")
    public ResponseEntity<List<PlaylistResponse>> getPlayListOfUser(@RequestParam() String username){
        List<PlaylistResponse> response = service.getPlaylistsOfUser(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "deletes playlist of requested playlistId from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Playlist with playlistId not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})})
    @DeleteMapping(path = "/delete/{playlistId}")
    public ResponseEntity<String> deletePlaylist(@PathVariable(name = "playlistId") long playlistId){
        service.deletePlaylist(playlistId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
    }

}
