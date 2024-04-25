package com.naveen.jukebox.repository;

import com.naveen.jukebox.entity.PlaylistsEntity;
import com.naveen.jukebox.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends CrudRepository<PlaylistsEntity, Long> {
    Optional<List<PlaylistsEntity>> findByTitle(String title);
    List<PlaylistsEntity> findByUser(UserEntity user);
}
