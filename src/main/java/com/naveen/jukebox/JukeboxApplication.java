package com.naveen.jukebox;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The main class that starts the Jukebox application.
 *
 * This class uses Spring Boot to initialize the application context and start the embedded web server.
 */
@SpringBootApplication
@EnableWebMvc
public class JukeboxApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(JukeboxApplication.class, args);
    }

    /**
     * Provides a ModelMapper bean that can be used to map between different model classes.
     *
     * <p>
     * Example usage:
     * <pre>
     * {@code
     * UserEntity entity = modelMapper.map(request, UserEntity.class);
     * }
     * </pre>
     * </p>
     *
     * @return The ModelMapper object.
     */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
