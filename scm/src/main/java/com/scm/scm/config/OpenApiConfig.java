package com.scm.scm.config;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WebFlux API - GraphQL")
                        .version("1.0")
                        .description("""
                                ## Dokumentation der GraphQL API  
                                
                                Diese API basiert auf **GraphQL**, wodurch alle Anfragen über einen **einzigen Endpunkt (`/graphql`)** verarbeitet werden.  
                                
                                ### Nutzung von Swagger UI für GraphQL:
                                - **Queries:** Dienen zum Abrufen von Daten.
                                - **Mutations:** Werden verwendet, um Daten zu ändern oder neue Einträge zu erstellen.
                                - Wählen Sie im **Request Body** aus, ob Sie eine Query oder Mutation ausführen möchten.
                                
                                **Hinweis:**  
                                Die Anfrage muss im **gültigen JSON-Format** gesendet werden.  
                                """));
    }
}
