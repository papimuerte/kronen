gateway\src\main\java\com\gateway\OpenApiConfig.java

package com.gateway;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getApiInfo())
                .tags(getApiTags())
                .paths(getPaths());
    }

    private Info getApiInfo() {
        return new Info()
                .title("API Dokumentation")
                .version("1.0")
                .description("""
                    ## Willkommen zur API-Dokumentation
                    
                    Diese API bietet eine umfassende Lösung für die Verwaltung von *Benutzern, Produkten und Bestellungen* in einem Großhandelssystem. 
                    Sie ermöglicht eine *nahtlose Integration über REST und GraphQL*, wodurch Entwickler flexibel auf die Daten zugreifen können.  
                    Admins können *Bestände verwalten, Benutzer steuern und Bestellungen überwachen*, während Kunden einfach und sicher Bestellungen aufgeben können.  
                    Durch moderne *Microservice-Architektur, JWT-Authentifizierung und OpenAPI-Spezifikation* wird eine *sichere und skalierbare Nutzung* gewährleistet.  
                    Nutze die API, um *Prozesse zu automatisieren, Bestellabläufe zu optimieren und die Effizienz deines Geschäfts zu steigern.*  
                    
                    ---
                    
                    ## GraphQL Queries
                    
                    ### 1. Create Order  
                    graphql
                    mutation CreateOrder { createOrder(input: {customerUsername: \\\"lala2\\\", companyName: \\\"lala\\\", email: \\\"lala@gmail.com\\\", address: \\\"Lala Street\\\", phoneNumber: \\\"123456789\\\", notes: \\\"lala\\\", products: [{ productId: \\\"J001\\\", quantity: 2 ,name: \\\"Diamond Ring\\\", unitPrice: 2000}]}) { id customerUsername totalAmount status createdAt products{ productId name quantity unitPrice   } } }
                    
    
                    ### 2. Update Order Status: Pending, Shipped, Done
                    graphql
                    mutation {updateOrderStatus(orderId: \\\"a02e87ff-258a-482e-8593-47ddb2017c85\\\", newStatus: \\\"Shipped\\\") {id status }}
                    
    
                    ### 3. Orders By Customer
                    graphql
                    query AllOrders { 
                      ordersByCustomer(customerUsername: \\\"lala2\\\") {  id  customerUsername  totalAmount  status  createdAt  companyName  email  address  phoneNumber  notes  products {    productId    name    quantity    unitPrice  }}} 
                    
    
                    ### 4. All Orders
                    graphql
                    query AllOrders {  allOrders {    id    customerUsername    totalAmount    status    createdAt    companyName    email    address    phoneNumber    notes    products { productId name quantity unitPrice} }}
                    
    
                    Diese API bietet sowohl REST- als auch GraphQL-Endpunkte für eine flexible Interaktion mit dem System.
                """);
    }
    

    private List<Tag> getApiTags() {
        return List.of(
                new Tag().name("Authentication").description("Endpoints for user authentication and token management"),
                new Tag().name("User Management").description("Endpoints for managing users"),
                new Tag().name("Product Management").description("Endpoints for managing products"),
                new Tag().name("Order Management").description("Endpoints for managing orders")
        );
    }

    private Paths getPaths() {
        Paths paths = new Paths();
        
        paths.addPathItem("/auth/register", createPostEndpoint("Authentication", "Registriert einen neuen Benutzer.", getUserRegistrationSchema()));
        paths.addPathItem("/auth/login", createPostEndpoint("Authentication", "Authentifiziert einen Benutzer und gibt ein JWT-Token zurück.", getLoginSchema()));
        
        paths.addPathItem("/users", createGetEndpoint("User Management", "Gibt eine Liste aller Benutzer zurück."));
        paths.addPathItem("/users/{id}", new PathItem()
                .get(createGetOperation("User Management", "Gibt die Details eines bestimmten Benutzers zurück. ID = username").addParametersItem(getIdParameter()))
                .put(createPutOperation("User Management", "Aktualisiert die Details eines Benutzers.", getUserUpdateSchema()).addParametersItem(getIdParameter()))
                .delete(createDeleteOperation("User Management", "Löscht einen Benutzer.").addParametersItem(getIdParameter())));
        
        paths.addPathItem("/api/products", createGetEndpoint("Product Management", "Gibt eine Liste aller gespeicherten Produkte zurück."));
        paths.addPathItem("/api/products/{id}", new PathItem()
                .get(createGetOperation("Product Management", "Gibt die Details eines bestimmten Produkts zurück.").addParametersItem(getIdParameter())));
        paths.addPathItem("/api/products/admin/{id}", new PathItem()
                .put(createPutOperation("Product Management", "Aktualisiert ein Produkt.", getProductSchema()).addParametersItem(getIdParameter()))
                .delete(createDeleteOperation("Product Management", "Löscht ein Produkt anhand seiner ID.").addParametersItem(getIdParameter())));
        paths.addPathItem("/api/products/admin/add", createPostEndpoint("Product Management", "Erstellt ein neues Produkt.", getProductSchema()));
        
        paths.addPathItem("/graphql", createPostEndpoint("Order Management", "GraphQL Endpoint für Bestellverwaltung.", getGraphQLQuerySchema()));
        
        return paths;
    }


    private Schema<?> getGraphQLQuerySchema() {
        return new Schema<>()
                .addProperty("query", new StringSchema().example("mutation CreateOrder { createOrder(input: {customerUsername: \"lala2\", companyName: \"lala\", email: \"lala@gmail.com\", address: \"Lala Street\", phoneNumber: \"123456789\", notes: \"lala\", products: [{ productId: \"J001\", quantity: 2 }]}) { id customerUsername totalAmount status createdAt products { productId name quantity unitPrice } } }"));
        }


    private Parameter getIdParameter() {
        return new Parameter()
                .name("id")
                .description("Die eindeutige Produkt/Benutzer-ID")
                .required(true)
                .in("path")
                .schema(new StringSchema().example("john_doe oder J001"));
    }

    private io.swagger.v3.oas.models.Operation createGetOperation(String tag, String description) {
        return new io.swagger.v3.oas.models.Operation()
                .summary(description)
                .tags(List.of(tag))
                .responses(getDefaultResponses());
    }

    private io.swagger.v3.oas.models.Operation createPutOperation(String tag, String description, Schema<?> schema) {
        return new io.swagger.v3.oas.models.Operation()
                .summary(description)
                .tags(List.of(tag))
                .requestBody(new RequestBody().content(getJsonContent(schema)))
                .responses(getDefaultResponses());
    }

    private io.swagger.v3.oas.models.Operation createDeleteOperation(String tag, String description) {
        return new io.swagger.v3.oas.models.Operation()
                .summary(description)
                .tags(List.of(tag))
                .responses(getDefaultResponses());
    }

    private PathItem createPostEndpoint(String tag, String description, Schema<?> schema) {
        return new PathItem().post(
                new io.swagger.v3.oas.models.Operation()
                        .summary(description)
                        .tags(List.of(tag))
                        .requestBody(schema != null ? new RequestBody().content(getJsonContent(schema)) : null)
                        .responses(getDefaultResponses()));
    }

    private PathItem createGetEndpoint(String tag, String description) {
        return new PathItem().get(
                new io.swagger.v3.oas.models.Operation()
                        .summary(description)
                        .tags(List.of(tag))
                        .responses(getDefaultResponses()));
    }

    private Content getJsonContent(Schema<?> schema) {
        return new Content().addMediaType("application/json", new MediaType().schema(schema));
    }

    private ApiResponses getDefaultResponses() {
        return new ApiResponses()
                .addApiResponse("200", new ApiResponse().description("Erfolgreiche Anfrage"))
                .addApiResponse("400", new ApiResponse().description("Ungültige Anfrage"))
                .addApiResponse("404", new ApiResponse().description("Benutzer nicht gefunden"))
                .addApiResponse("500", new ApiResponse().description("Interner Serverfehler"));
    }

    private Schema<?> getUserRegistrationSchema() {
        return new Schema<>()
                .addProperty("id", new StringSchema().example("john_doe"))
                .addProperty("username", new StringSchema().example("john_doe"))
                .addProperty("password", new StringSchema().example("securepassword"))
                .addProperty("email", new StringSchema().example("john.doe@example.com"))
                .addProperty("phoneNumber", new StringSchema().example("123456789"))
                .addProperty("address", new StringSchema().example("123 Main Street"))
                .addProperty("companyName", new StringSchema().example("ExampleCorp"));
    }

    private Schema<?> getLoginSchema() {
        return new Schema<>()
                .addProperty("username", new StringSchema().example("john_doe"))
                .addProperty("password", new StringSchema().example("securepassword"));
    }

    private Schema<?> getUserUpdateSchema() {
        return new Schema<>()
                .addProperty("email", new StringSchema().example("new.email@example.com"))
                .addProperty("phoneNumber", new StringSchema().example("123456789"));
    }

    private Schema<?> getProductSchema() {
        return new Schema<>()
                .addProperty("productId", new StringSchema().example("J001"))
                .addProperty("name", new StringSchema().example("Diamond Ring"))
                .addProperty("description", new StringSchema().example("Elegant 18k white gold ring with diamonds"))
                .addProperty("category", new StringSchema().example("Rings"))
                .addProperty("material", new StringSchema().example("18k White Gold, Diamond"))
                .addProperty("unitPrice", new StringSchema().example("4000.0"))
                .addProperty("currency", new StringSchema().example("EUR"))
                .addProperty("availableQuantity", new StringSchema().example("29"))
                .addProperty("minimumOrderQuantity", new StringSchema().example("1"))
                .addProperty("supplier", new StringSchema().example("GemLux Creations"))
                .addProperty("leadTimeDays", new StringSchema().example("4"))
                .addProperty("weightGram", new StringSchema().example("4"));
    }
}