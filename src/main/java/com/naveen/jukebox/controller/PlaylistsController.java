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

/**
 * Controller for managing playlist operations in the Jukebox application.
 * <p>
 * This controller provides endpoints for creating playlists, adding songs to playlists, retrieving playlists,
 * and deleting playlists. It interacts with the PlaylistService to perform these operations and returns appropriate
 * HTTP responses.
 * </p>
 */
@RestController
@RequestMapping(path = "/playlists")
public class PlaylistsController {

    private final PlaylistService service;

    /**
     * Constructs a new PlaylistController with the specified PlaylistService.
     *
     * @param service the PlaylistService used to perform playlist operations
     */
    @Autowired
    public PlaylistsController(PlaylistService service) {
        this.service = service;
    }

    /**
     * Creates a new playlist for the specified user.
     * <p>
     * This endpoint accepts a username and a playlist request in the body. It uses the service layer to create a
     * new playlist for the given user. If the playlist is successfully created, it returns a response entity
     * with a success message and a 201 Created status.
     * </p>
     *
     * @param username the username of the user for whom the playlist is being created
     * @param playlist the playlist request object containing the details of the playlist to be created
     * @return a ResponseEntity containing a success message and HTTP status 201 (Created)
     */
    @Operation(summary = "Creates a playlist of songs for the given user.", description = "Creates a playlist of songs for the given user. Ensure to specify the proper username.")
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

    /**
     * Adds a song to a specified playlist.
     * <p>
     * This endpoint allows the user to add an existing song to an existing playlist by specifying the playlist ID and song ID.
     * </p>
     * <p>
     * The method uses the service layer to perform the addition and returns a response indicating the result of the operation.
     * </p>
     *
     * @param playlistId the ID of the playlist to which the song will be added
     * @param songId the ID of the song to be added to the playlist
     * @return a ResponseEntity containing a success message and HTTP status 202 (Accepted) if the operation is successful
     */
    @Operation(summary = "adds song to playlist.", description = "adds song to playlist. Ensure to specify the proper username and existing song.")
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

    /**
     * Retrieves a playlist by its ID.
     * <p>
     * This endpoint allows the user to retrieve the details of a specified playlist by providing the playlist ID.
     * The method uses the service layer to fetch the playlist details and returns a response containing the playlist information.
     * </p>
     *
     * @param playlistId the ID of the playlist to be retrieved
     * @return a ResponseEntity containing a PlaylistResponse object with the playlist details and HTTP status 200 (OK) if the operation is successful
     */
    @Operation(summary = "gets playlist of requested playlistId from the database.", description = "gets all the playlists of requested user. Ensure to specify the proper username.")
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

    /**
     * Retrieves all playlists for a specified user.
     * <p>
     * This endpoint allows the user to retrieve a list of all playlists associated with a given username.
     * The method uses the service layer to fetch the playlists and returns a response containing the list of playlists.
     * </p>
     *
     * @param username the username of the user whose playlists are to be retrieved
     * @return a ResponseEntity containing a list of PlaylistResponse objects with the user's playlists and HTTP status 200 (OK) if the operation is successful
     */
    @Operation(summary = "gets all the playlists of requested user.", description = "gets all the playlists of requested user. Ensure to specify the proper username.")
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

    /**
     * Deletes a specified playlist.
     * <p>
     * This endpoint allows the user to delete a playlist based on the provided playlist ID.
     * The method uses the service layer to perform the deletion and returns a response indicating
     * whether the deletion was successful.
     * </p>
     *
     * @param playlistId the ID of the playlist to be deleted
     * @return a ResponseEntity containing a success message and HTTP status 204 (No Content) if the operation is successful
     */
    @Operation(summary = "deletes playlist of requested playlistId from the database.", description = "deletes playlist of requested playlistId from the database. Ensure to specify the proper exiting playlistId.")
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
