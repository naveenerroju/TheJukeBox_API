package com.naveen.jukebox.repository;

import com.naveen.jukebox.entity.SongsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Songs entities.
 * Extends Spring Data's CrudRepository which provides basic CRUD operations.
 */
@Repository
public interface SongsRepository extends CrudRepository<SongsEntity, Long> {

    /**
     * Retrieves a song by its title.
     *
     * @param title The title of the song to retrieve.
     * @return An Optional containing the SongsEntity with the given title, if found.
     */
    Optional<SongsEntity> findByTitle(String title);
}
