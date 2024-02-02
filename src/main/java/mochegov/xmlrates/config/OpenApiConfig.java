package mochegov.xmlrates.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Publishing of currency rates")
                    .version("1.0.1")
                    .contact(
                        new Contact()
                            .email("Dmitry.MOCHEGOV@raiffeisen.ru")
                            .url("https://www.raiffeisen.ru/")
                            .name("MOCHEGOV Dmitry")
                    )
            );
    }
}
