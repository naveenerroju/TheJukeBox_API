package com.naveen.jukebox;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * TODO:
 *      Add constructor parameters
 *      Make the requests return a valid response
 *      Document all the methods
 *      Users table should have all the playlists references
 *      Playlists table should have all the songs references
 *      Songs table should have all the playlists references
 */
@SpringBootApplication
public class JukeboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(JukeboxApplication.class, args);
    }

    /**
     * Use this Bean to use the mapper.
     * <br>
     * <p>
     * Autowire the ModelMapper. Then use it to map the models.
     * Example: <code>UserEntity entity = this.modelMapper.map(request, UserEntity.class);</code>
     * </p>
     *
     * @return the ModelMapper object
     */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
