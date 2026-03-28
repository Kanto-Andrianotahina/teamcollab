package mg.teamcollab.restapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI teamCollabOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TeamCollab API")
                        .version("1.0")
                        .description("API REST sécurisée de gestion collaborative de projets, tâches, membres et commentaires")
                        .contact(new Contact()
                                .name("TeamCollab - MBDS M2")
                                .email("teamcollab@mbds.local"))
                        .license(new License()
                                .name("Academic Project")));
    }
}