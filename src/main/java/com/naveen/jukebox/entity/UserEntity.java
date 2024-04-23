package com.naveen.jukebox.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.naveen.jukebox.model.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "username must not be null or empty")
    @Size(min=4, max=10, message="Username must be between 4 and 10 characters")
    @Column(name = "USERNAME")
    private String username;
    @NotEmpty(message = "password must not be null or empty")
    @Size(min=4, max=10, message="Password must be between 4 and 10 characters")
    @Column(name = "PASSWORD")
    private String password;
    @NotEmpty(message = "user's role must not be null or empty")
    @Column(name = "USER_ROLE")
    private UserRole role;
    @NotEmpty(message = "name must not be null or empty")
    @Size(min=3, max=20, message="Name must be between 3 and 20 characters")
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaylistsEntity> playlists;

}
