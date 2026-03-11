package se.lexicon.ecommerce.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * OpenAPI/Swagger configuration for public REST endpoints.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Builds base OpenAPI metadata used by Swagger UI.
     *
     * @return configured {@link OpenAPI} instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce API")
                        .version("1.0")
                        .description("API documentation for the E-commerce application"));
    }

    /**
     * Defines the public API group exposed in Swagger.
     *
     * @return configured {@link GroupedOpenApi} group
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/v1/**")
                .build();
    }

}
