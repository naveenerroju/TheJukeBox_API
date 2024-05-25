package com.naveen.jukebox.repository;

import com.naveen.jukebox.entity.PlaylistsEntity;
import com.naveen.jukebox.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for managing Playlist entities.
 * Extends Spring Data's CrudRepository which provides basic CRUD operations.
 */
public interface PlaylistRepository extends CrudRepository<PlaylistsEntity, Long> {

    /**
     * Retrieves all playlists associated with a given user.
     *
     * @param user The user entity for whom playlists are to be retrieved.
     * @return A list of PlaylistsEntity associated with the given user.
     */
    List<PlaylistsEntity> findByUser(UserEntity user);
}
