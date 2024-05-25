package com.naveen.jukebox.service;

import com.naveen.jukebox.entity.PlaylistsEntity;
import com.naveen.jukebox.entity.SongsEntity;
import com.naveen.jukebox.entity.UserEntity;
import com.naveen.jukebox.exceptions.IncorrectInputsException;
import com.naveen.jukebox.model.PlayListRequest;
import com.naveen.jukebox.model.PlaylistResponse;
import com.naveen.jukebox.repository.PlaylistRepository;
import com.naveen.jukebox.repository.SongsRepository;
import com.naveen.jukebox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SongsRepository songsRepository;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private UserService userService;

    @Mock
    private SongsService songsService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlaylistService playlistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Operation should succeed properly")
    public void testCreatePlaylist_Success() {
        // Mock data
        PlayListRequest request = new PlayListRequest();
        request.setTitle("Test Playlist");
        request.setSongs(Arrays.asList(1L, 2L));

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        SongsEntity song1 = new SongsEntity();
        song1.setId(1L);
        song1.setTitle("Song 1");

        SongsEntity song2 = new SongsEntity();
        song2.setId(2L);
        song2.setTitle("Song 2");

        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        playlistsEntity.setTitle(request.getTitle());
        playlistsEntity.setUser(user);
        playlistsEntity.setSongs(new ArrayList<>()); // Initialize songs list

        // Mock behavior
        when(userService.getUserWithUsername("testuser")).thenReturn(user);
        when(songsService.getSongById(1L)).thenReturn(song1);
        when(songsService.getSongById(2L)).thenReturn(song2);
        when(modelMapper.map(request, PlaylistsEntity.class)).thenReturn(playlistsEntity);

        // Call the service method
        assertDoesNotThrow(() -> playlistService.createPlaylist("testuser", request));

        // Verify repository interactions
        verify(userService, times(1)).getUserWithUsername("testuser");
        verify(songsService, times(1)).getSongById(1L);
        verify(songsService, times(1)).getSongById(2L);
        verify(playlistRepository, times(1)).save(playlistsEntity);
    }


    @Test
    @DisplayName("Service should throw User Not Found error")
    void testCreatePlaylist_UserNotFound() {
        // Mock data
        PlayListRequest request = new PlayListRequest();
        request.setTitle("Test Playlist");
        request.setSongs(Collections.singletonList(1L));

        // Mock behavior
        when(userService.getUserWithUsername("testuser")).thenReturn(null);

        // Call the service method and assert exception
        IncorrectInputsException exception = assertThrows(IncorrectInputsException.class,
                () -> playlistService.createPlaylist("testuser", request));

        assertEquals("User with username testuser does not exist", exception.getMessage());

        // Verify repository interactions
        verify(userService, times(1)).getUserWithUsername("testuser");
        verify(songsService, never()).getSongById(anyLong());
        verify(playlistRepository, never()).save(any());
    }

    @Test
    @DisplayName("Service should throw Song Not Found error")
    void testCreatePlaylist_SongNotFound() {
        // Mock data
        PlayListRequest request = new PlayListRequest();
        request.setTitle("Test Playlist");
        request.setSongs(Collections.singletonList(1L));

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        SongsEntity song1 = new SongsEntity();
        song1.setId(1L);
        song1.setTitle("Song 1");

        SongsEntity song2 = new SongsEntity();
        song2.setId(2L);
        song2.setTitle("Song 2");

        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        playlistsEntity.setTitle(request.getTitle());
        playlistsEntity.setUser(user);
        playlistsEntity.setSongs(Arrays.asList(song1, song2));

        // Mock behavior
        when(userService.getUserWithUsername("testuser")).thenReturn(user);
        when(songsService.getSongById(1L)).thenReturn(null);
        when(modelMapper.map(request, PlaylistsEntity.class)).thenReturn(playlistsEntity);

        // Call the service method and assert exception
        IncorrectInputsException exception = assertThrows(IncorrectInputsException.class,
                () -> playlistService.createPlaylist("testuser", request));

        assertEquals("Song with ID 1 not found", exception.getMessage());

        // Verify repository interactions
        verify(userService, times(1)).getUserWithUsername("testuser");
        verify(songsService, times(1)).getSongById(1L);
        verify(playlistRepository, never()).save(any());
    }

    @Test
    @DisplayName("Add Song to Playlist - Success")
    void testAddSongToPlaylist_Success() {
        // Mock data
        long playlistId = 1L;
        long songId = 10L;

        SongsEntity song = new SongsEntity();
        song.setId(songId);
        song.setTitle("Test Song");
        List<SongsEntity> songs = new ArrayList<>(List.of(song));

        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        playlistsEntity.setId(playlistId);
        playlistsEntity.setSongs(songs);

        // Mock behavior
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlistsEntity));
        when(songsService.getSongById(songId)).thenReturn(song);

        // Call the service method
        playlistService.addSongToPlaylist(playlistId, songId);

        // Verify interactions
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(songsService, times(1)).getSongById(songId);
        verify(playlistRepository, times(1)).save(playlistsEntity);
    }

    @Test
    @DisplayName("Add Song to Playlist - Playlist Not Found")
    void testAddSongToPlaylist_PlaylistNotFound() {
        // Mock data
        long playlistId = 1L;
        long songId = 10L;

        // Mock behavior
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // Call the service method and assert exception
        assertThrows(IncorrectInputsException.class, () -> {
            playlistService.addSongToPlaylist(playlistId, songId);
        });

        // Verify interactions
        verify(playlistRepository, times(1)).findById(playlistId);
        verifyNoMoreInteractions(songsService);
    }

    @Test
    @DisplayName("Add Song to Playlist - Song Not Found")
    void testAddSongToPlaylist_SongNotFound() {
        // Mock data
        long playlistId = 1L;
        long songId = 10L;

        SongsEntity song = new SongsEntity();
        song.setId(songId);
        song.setTitle("Test Song");
        List<SongsEntity> songs = new ArrayList<>(List.of(song));

        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        playlistsEntity.setId(playlistId);
        playlistsEntity.setSongs(songs);

        // Mock behavior
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlistsEntity));
        when(songsService.getSongById(songId)).thenThrow(new IncorrectInputsException("Song id " + songId + " not found"));

        // Call the service method and assert exception
        assertThrows(IncorrectInputsException.class, () -> {
            playlistService.addSongToPlaylist(playlistId, songId);
        });

        // Verify interactions
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(songsService, times(1)).getSongById(songId);
        verifyNoMoreInteractions(playlistRepository);
    }

    @Test
    @DisplayName("Get Playlist - Success")
    void testGetPlaylist_Success() throws IncorrectInputsException {
        // Mock data
        long playlistId = 1L;
        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        playlistsEntity.setId(playlistId);
        playlistsEntity.setTitle("Test Playlist");

        // Mock behavior
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlistsEntity));
        when(modelMapper.map(playlistsEntity, PlaylistResponse.class)).thenReturn(new PlaylistResponse(playlistId, playlistsEntity.getTitle(), playlistsEntity.getSongs(), playlistsEntity.getUser()));

        // Call the service method
        PlaylistResponse response = playlistService.getPlaylist(playlistId);

        // Verify interactions and assertions
        verify(playlistRepository, times(1)).findById(playlistId);
        assertEquals(playlistsEntity.getId(), response.getId());
        assertEquals(playlistsEntity.getTitle(), response.getTitle());
    }

    @Test
    @DisplayName("Get Playlist - Playlist Not Found")
    void testGetPlaylist_PlaylistNotFound() {
        // Mock data
        long playlistId = 1L;

        // Mock behavior
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // Call the service method and assert exception
        assertThrows(IncorrectInputsException.class, () -> {
            playlistService.getPlaylist(playlistId);
        });

        // Verify interactions
        verify(playlistRepository, times(1)).findById(playlistId);
    }

    @Test
    @DisplayName("Get Playlists of User - Success")
    void testGetPlaylistsOfUser_Success() throws IncorrectInputsException {
        // Mock data
        String username = "testuser";
        UserEntity user = new UserEntity();
        user.setUsername(username);

        List<PlaylistsEntity> playlistsEntities = new ArrayList<>();
        PlaylistsEntity playlist1 = new PlaylistsEntity();
        playlist1.setId(1L);
        playlist1.setTitle("Playlist 1");
        playlist1.setUser(user);

        PlaylistsEntity playlist2 = new PlaylistsEntity();
        playlist2.setId(2L);
        playlist2.setTitle("Playlist 2");
        playlist2.setUser(user);

        playlistsEntities.add(playlist1);
        playlistsEntities.add(playlist2);

        // Mock behavior
        when(userService.getUserWithUsername(username)).thenReturn(user);
        when(playlistRepository.findByUser(user)).thenReturn(playlistsEntities);
        when(modelMapper.map(any(PlaylistsEntity.class), eq(PlaylistResponse.class)))
                .thenReturn(new PlaylistResponse());

        // Call the service method
        List<PlaylistResponse> response = playlistService.getPlaylistsOfUser(username);

        // Verify interactions and assertions
        verify(userService, times(1)).getUserWithUsername(username);
        verify(playlistRepository, times(1)).findByUser(user);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("Get Playlists of User - User Not Found")
    void testGetPlaylistsOfUser_UserNotFound() {
        // Mock data
        String username = "nonexistentuser";

        // Mock behavior
        when(userService.getUserWithUsername(username)).thenThrow(new IncorrectInputsException("User not found"));

        // Call the service method and assert exception
        assertThrows(IncorrectInputsException.class, () -> {
            playlistService.getPlaylistsOfUser(username);
        });

        // Verify interactions
        verify(userService, times(1)).getUserWithUsername(username);
        verifyNoInteractions(playlistRepository);
    }

    @Test
    @DisplayName("Delete Playlist - Success")
    void testDeletePlaylist_Success() throws IncorrectInputsException {
        // Mock data
        long playlistId = 1L;
        PlaylistsEntity playlist = new PlaylistsEntity();
        playlist.setId(playlistId);

        // Mock behavior
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        // Call the service method
        playlistService.deletePlaylist(playlistId);

        // Verify interactions
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).deleteById(playlistId);
    }

    @Test
    @DisplayName("Delete Playlist - Playlist Not Found")
    void testDeletePlaylist_PlaylistNotFound() {
        // Mock data
        long playlistId = 1L;

        // Mock behavior
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // Call the service method and assert exception
        assertThrows(IncorrectInputsException.class, () -> {
            playlistService.deletePlaylist(playlistId);
        });

        // Verify interactions
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(0)).deleteById(playlistId);
    }



}
