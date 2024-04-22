package com.naveen.jukebox.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @NotNull
    @Size(min=4, max=10, message="Username must be between 4 and 10 characters")
    @Column(name = "USERNAME")
    private String username;
    @NotNull
    @Size(min=4, max=10, message="Password must be between 4 and 10 characters")
    @Column(name = "PASSWORD")
    private String password;
    @NotNull
    @Size(min=3, max=20, message="Name must be between 3 and 20 characters")
    @Column(name = "NAME")
    private String name;

}
