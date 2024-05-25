package com.naveen.jukebox.repository;

import com.naveen.jukebox.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * Extends Spring Data's CrudRepository which provides basic CRUD operations.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return An Optional containing the UserEntity with the given username, if found.
     */
    Optional<UserEntity> findByUsername(String username);
}
