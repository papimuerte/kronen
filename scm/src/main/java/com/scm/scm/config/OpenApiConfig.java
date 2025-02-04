package com.scm.scm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
                                
                                ### Beispiel für eine Query (Daten abrufen)
                                ```json
                                {
                                  "query": "query Order { ordersByCustomer(customerUsername: \\"user2\\") { id customerUsername totalAmount status createdAt products { productId name quantity unitPrice } } }",
                                  "variables": {}
                                }
                                ```
                                
                                ### Beispiel für eine Mutation (Daten ändern oder erstellen)
                                ```json
                                {
                                  "query": "mutation CreateOrder { createOrder(input: {customerUsername: \\"lala\\", companyName: \\"lala\\", email: \\"lala@gmail.com\\", address: \\"Lala Street\\", phoneNumber: \\"123456789\\", notes: \\"lala\\", products: [{ productId: \\"J001\\", quantity: 2 }]}) { id customerUsername totalAmount status createdAt products { productId name quantity unitPrice } } }",
                                  "variables": {}
                                }
                                ```
                                """))
                .paths(getGraphQLPaths());
    }

    private Paths getGraphQLPaths() {
        Paths paths = new Paths();

        paths.addPathItem("/graphql", new PathItem()
                .post(new io.swagger.v3.oas.models.Operation()
                        .summary("GraphQL Query und Mutation Endpunkt")
                        .description("""
                                Dieser Endpunkt verarbeitet sowohl **GraphQL Queries** als auch **Mutations**.  
                                - **Queries** werden verwendet, um Daten aus der API abzurufen.  
                                - **Mutations** dienen dazu, Daten zu verändern oder neue Einträge zu erstellen.  
                                - Wählen Sie im **Request Body** aus, welche Art von Anfrage Sie senden möchten.
                                """)
                        .requestBody(new RequestBody()
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .oneOf(List.of(
                                                                        // Option 1: GraphQL Query ohne Beispiel im Request Body
                                                                        new Schema<>()
                                                                                .description("GraphQL Query (Abrufen von Daten)")
                                                                                .addProperty("query", new StringSchema()),

                                                                        // Option 2: GraphQL Mutation ohne Beispiel im Request Body
                                                                        new Schema<>()
                                                                                .description("GraphQL Mutation (Verändern oder Erstellen von Daten)")
                                                                                .addProperty("query", new StringSchema())
                                                                ))
                                                        ))))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("Erfolgreiche Antwort")
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema<>()
                                                                .addProperty("data", new StringSchema()))))))));

        return paths;
    }
}
