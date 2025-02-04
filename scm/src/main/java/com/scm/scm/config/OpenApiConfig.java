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
                        .title("WebFlux API")
                        .version("1.0")
                        .description("""
                                ## Willkommen zur API-Dokumentation 🚀  
                                 
                                Diese API unterstützt sowohl **GraphQL** als auch **gRPC**.  
                                 
                                ### Verwendung von GraphQL:
                                - **Abfragen (Queries):** Mit `query` können Daten abgerufen werden.
                                - **Mutationen (Mutations):** Mit `mutation` können Daten geändert werden.
                                - **Alle Anfragen müssen im gültigen JSON-Format gesendet werden.**

                                Example Query Request:
                                ```json
                                {
                                  "query": "query Order { ordersByCustomer(customerUsername: \\"user2\\") { id customerUsername totalAmount status createdAt products { productId name quantity unitPrice } } }",
                                  "variables": {}
                                }
                                ```
                                
                                Example Mutation Request:
                                ```json
                                {
                                  "query": "mutation CreateOrder { createOrder(input: {customerUsername: \\"lala\\", companyName: \\"lala\\", email: \\"lala@gmail.com\\", address: \\"Lala Street\\", phoneNumber: \\"123456789\\", notes: \\"lala\\", products: [{ productId: \\"J001\\", quantity: 2 }]}) { id customerUsername totalAmount status createdAt products { productId name quantity unitPrice } } }",
                                  "variables": {}
                                }
                                ```
                                 
                                ### Verwendung von gRPC:
                                - **Swagger UI unterstützt kein direktes gRPC-Testing.**
                                - **Alternativ können gRPC-Endpunkte in Postman mit folgenden JSON-Bodies getestet werden:**
                                
                                **Bestand prüfen:**
                                ```json
                                {
                                    "productId": "J002"
                                }
                                ```
                                
                                **Bestand aktualisieren:**
                                ```json
                                {
                                    "productId": "J002",
                                    "quantity": 10
                                }
                                ```
                                """))
                .paths(getPaths());
    }

    private Paths getPaths() {
        Paths paths = new Paths();

        paths.addPathItem("/graphql", new PathItem()
                .post(new io.swagger.v3.oas.models.Operation()
                        .summary("GraphQL Abfrage- und Mutations-Endpunkt")
                        .description("""
                                Dieser Endpunkt unterstützt **GraphQL-Abfragen und -Mutationen**.  
                                - Verwenden Sie **GraphQL-Abfragen**, um Daten abzurufen.  
                                - Verwenden Sie **GraphQL-Mutationen**, um Daten zu ändern.  
                                - Wählen Sie aus den **unten stehenden Beispielanfragen** eine passende Anfrage aus.
                                """)
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("Erfolgreiche Antwort")
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema<>()
                                                                .addProperty("data", new StringSchema()
                                                                        .example("""
                                                                                {
                                                                                    "data": {
                                                                                        "ordersByCustomer": [
                                                                                            {
                                                                                                "id": "101",
                                                                                                "customerUsername": "user1",
                                                                                                "totalAmount": 5500,
                                                                                                "status": "Abgeschlossen",
                                                                                                "createdAt": "2024-12-20T10:30:00Z",
                                                                                                "products": [
                                                                                                    {
                                                                                                        "productId": "J001",
                                                                                                        "name": "Diamant-Verlobungsring",
                                                                                                        "quantity": 2,
                                                                                                        "unitPrice": 2500
                                                                                                    }
                                                                                                ]
                                                                                            }
                                                                                        ]
                                                                                    }
                                                                                }
                                                                                """)))))))));

        // gRPC-Endpunkt-Dokumentation (nicht testbar in Swagger, nur informativ)
        paths.addPathItem("localhost:9090/InventoryService/CheckAvailability", new PathItem()
                .post(new io.swagger.v3.oas.models.Operation()
                        .summary("gRPC: Produktverfügbarkeit prüfen")
                        .description("""
                                Diese Methode wird verwendet, um die Verfügbarkeit eines Produkts zu prüfen.  
                                **Kann nicht direkt in Swagger UI getestet werden.**  
                                
                                **Alternativ kann dieser Endpunkt in Postman getestet werden, indem folgender JSON-Body gesendet wird:**
                                ```json
                                {
                                    "productId": "J002"
                                }
                                ```
                                """)
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("Antwort vom gRPC-Server (nur über gRPC-Client abrufbar)")))));

        paths.addPathItem("/localhost:9090/InventoryService/UpdateInventory", new PathItem()
                .post(new io.swagger.v3.oas.models.Operation()
                        .summary("gRPC: Inventar aktualisieren")
                        .description("""
                                Diese Methode wird verwendet, um das Inventar eines Produkts zu aktualisieren.  
                                **Kann nicht direkt in Swagger UI getestet werden.**  
                                
                                **Alternativ kann dieser Endpunkt in Postman getestet werden, indem folgender JSON-Body gesendet wird:**
                                ```json
                                {
                                    "productId": "J002",
                                    "quantity": 10
                                }
                                ```
                                
                                **Erwartete Antwort:**
                                ```json
                                {
                                    "productId": "J002",
                                    "success": true,
                                    "message": "Inventar erfolgreich aktualisiert"
                                }
                                ```
                                """)
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("Antwort vom gRPC-Server (nur über gRPC-Client abrufbar)")))));

        return paths;
    }
}
