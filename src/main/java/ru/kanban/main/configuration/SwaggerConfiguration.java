package ru.kanban.main.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * The type Swagger configuration.
 */
@Configuration
public class SwaggerConfiguration {
    /**
     * Main api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket mainApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.kanban.main.web"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Backend REST API",
                "Describes API for backend of Kanban Project",
                null, null, null, null, null, Collections.emptyList()
        );
    }
}
