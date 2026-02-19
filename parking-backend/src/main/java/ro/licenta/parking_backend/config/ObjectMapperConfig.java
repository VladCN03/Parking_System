package ro.licenta.parking_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // JsonMapper este implementarea modernă pentru JSON
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }
}
