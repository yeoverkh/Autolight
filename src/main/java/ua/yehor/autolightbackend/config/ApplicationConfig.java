package ua.yehor.autolightbackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration class for the Autolight application.
 * Configures various beans required for the application including OpenAPI definitions,
 * message sources, and validators.
 */
@Configuration
public class ApplicationConfig {
    /**
     * Creates and configures an OpenAPI definition for Autolight.
     *
     * @param apiVersion the version of the API
     * @return OpenAPI object defining security, components, and API information
     */
    @Bean
    public OpenAPI openAPI(@Value("${api.version}") String apiVersion) {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Autolight")
                        .description("All endpoints in autolight.")
                        .version(apiVersion));
    }

    /**
     * Creates a SecurityScheme for API key-based authentication.
     *
     * @return SecurityScheme defining bearer token authentication
     */
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    /**
     * Provides message sources for error handling and localization.
     *
     * @return MessageSource for handling error messages
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("errors");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Configures a LocalValidatorFactoryBean using the provided MessageSource for validation messages.
     *
     * @param messageSource the MessageSource containing validation messages
     * @return LocalValidatorFactoryBean configured with the specified MessageSource
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
