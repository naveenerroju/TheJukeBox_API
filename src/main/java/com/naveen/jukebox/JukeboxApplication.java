package com.naveen.jukebox;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class JukeboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(JukeboxApplication.class, args);
    }

    /**
     * Use this Bean to use the mapper.
     * <br>
     * <p>
     * Autowire the ModelMapper. Then use it to map the models.
     * Example: <code>
     *     <br>
     *     UserEntity entity = this.modelMapper.map(request, UserEntity.class);
     * </code>
     * </p>
     *
     * @return the ModelMapper object
     */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
