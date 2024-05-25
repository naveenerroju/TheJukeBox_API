package com.naveen.jukebox.controller;

import com.naveen.jukebox.model.SongsRequest;
import com.naveen.jukebox.model.SongsResponse;
import com.naveen.jukebox.service.SongsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing songs in the Jukebox application.
 * <p>
 * This controller provides endpoints for adding, retrieving, and deleting songs.
 * It interacts with the SongsService to perform these operations and returns the appropriate HTTP responses.
 * </p>
 */
@RestController
@RequestMapping("/songs")
public class SongsController {

    private final SongsService songsService;

    /**
     * Constructor for SongsController.
     *
     * @param songsService the service to handle song-related operations
     */
    public SongsController(SongsService songsService) {
        this.songsService = songsService;
    }

    /**
     * Adds a new song.
     * <p>
     * This endpoint accepts a SongsRequest object containing song details and returns a SongsResponse object
     * with the details of the added song.
     * </p>
     *
     * @param request the SongsRequest object containing the song's details
     * @return a ResponseEntity containing the SongsResponse and HTTP status 201 (Created) if the operation is successful
     */
    @Operation(summary = "Adds a new song.", description = "Adds a new song with the given details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Song successfully added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid song details provided",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    @PostMapping()
    public ResponseEntity<SongsResponse> addSongs(@RequestBody SongsRequest request) {
        SongsResponse response = songsService.addSong(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    /**
     * Adds multiple new songs.
     * <p>
     * This endpoint accepts a list of SongsRequest objects containing song details and returns a list of
     * SongsResponse objects with the details of the added songs.
     * </p>
     *
     * @param request the list of SongsRequest objects containing the songs' details
     * @return a ResponseEntity containing the list of SongsResponse and HTTP status 201 (Created) if the operation is successful
     */
    @Operation(summary = "Adds multiple new songs.", description = "Adds multiple new songs with the given details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Songs successfully added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid song details provided",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    @PostMapping(path = "/add-all")
    public ResponseEntity<List<SongsResponse>> addMultipleSongs(@RequestBody List<SongsRequest> request) {
        List<SongsResponse> response = songsService.addMultipleSongs(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    /**
     * Retrieves all songs.
     * <p>
     * This endpoint returns a list of all SongsResponse objects representing all songs in the system.
     * </p>
     *
     * @return a ResponseEntity containing the list of SongsResponse and HTTP status 200 (OK)
     */
    @Operation(summary = "Retrieves all songs.", description = "Retrieves a list of all songs in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Songs successfully retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))})
    })
    @GetMapping(path = "/getall")
    public ResponseEntity<List<SongsResponse>> getAllSongs() {
        List<SongsResponse> responses = songsService.getSongs();
        return new ResponseEntity<>(responses, HttpStatusCode.valueOf(200));
    }

    /**
     * Deletes a song.
     * <p>
     * This endpoint deletes the song with the specified ID and returns a confirmation message.
     * </p>
     *
     * @param songId the ID of the song to delete
     * @return a ResponseEntity containing a confirmation message and HTTP status 204 (No Content) if the operation is successful
     */
    @Operation(summary = "Deletes a song.", description = "Deletes the song with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Song successfully deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Song not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    @DeleteMapping(path = "/{songId}")
    public ResponseEntity<String> deleteSong(@PathVariable(name = "songId") long songId) {
        songsService.deleteSong(songId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
    }

}
