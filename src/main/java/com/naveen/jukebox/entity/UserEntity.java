package com.naveen.jukebox.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.naveen.jukebox.model.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Username must not be null or empty")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotEmpty(message = "Password must not be null or empty")
    @Size(min = 4, max = 10, message = "Password must be between 4 and 10 characters")
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NotEmpty(message = "User's role must not be null or empty")
    @Column(name = "USER_ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotEmpty(message = "Name must not be null or empty")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PlaylistsEntity> playlists;

}