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
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Map;

/**
 * Configures OpenAPI documentation for the API gateway.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates the OpenAPI configuration.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getApiInfo())
                .tags(getApiTags())
                .paths(getPaths());
    }

    /**
     * Defines API metadata.
     */
    private Info getApiInfo() {
        return new Info()
                .title("SCM Gateway API")
                .version("1.0")
                .description("""
                    ## API Documentation 🚀
                    
                    This API manages multiple microservices:
                    - **GraphQL** for flexible queries
                    - **REST APIs** for authentication, user, and product management
                    - **gRPC** for high-performance service communication
                    
                    **Use REST endpoints for standard operations or GraphQL for complex queries.**
                    """);
    }

    /**
     * Defines API tags.
     */
    private List<Tag> getApiTags() {
        return List.of(
                new Tag().name("Authentication").description("User authentication endpoints"),
                new Tag().name("User Management").description("User management endpoints"),
                new Tag().name("Product Management").description("Product management endpoints"),
                new Tag().name("GraphQL API").description("GraphQL query execution"),
                new Tag().name("gRPC Services").description("gRPC-based services")
        );
    }

    /**
     * Defines API paths.
     */
    private Paths getPaths() {
        Paths paths = new Paths();
        
        Map<String, String> authEndpoints = Map.of(
                "/auth/register", "Registers a new user.",
                "/auth/login", "Authenticates a user and returns a JWT token."
        );
        authEndpoints.forEach((path, desc) ->
                paths.addPathItem(path, createPostEndpoint("Authentication", desc, getAuthSchema())));
        
        paths.addPathItem("/api/products", createGetEndpoint("Product Management", "Returns all stored products."));
        paths.addPathItem("/api/products/{id}", createGetEndpoint("Product Management", "Returns product details."));
        paths.addPathItem("/api/products/admin/add", createPostEndpoint("Product Management", "Creates a new product.", getProductSchema()));
        paths.addPathItem("/api/products/admin/{id}", createPutEndpoint("Product Management", "Updates a product.", getProductSchema()));
        paths.addPathItem("/api/products/admin/{id}", createDeleteEndpoint("Product Management", "Deletes a product."));

        paths.addPathItem("/users", createGetEndpoint("User Management", "Returns all users."));
        paths.addPathItem("/users/{id}", createGetEndpoint("User Management", "Returns user details."));
        paths.addPathItem("/users/{id}", createPutEndpoint("User Management", "Updates user details.", getAuthSchema()));
        paths.addPathItem("/users/{id}", createDeleteEndpoint("User Management", "Deletes a user."));

        paths.addPathItem("/graphql", createPostEndpoint("GraphQL API", "Processes GraphQL queries.", null));

        paths.addPathItem("localhost:9090/InventoryService/CheckAvailability", createGrpcEndpoint("gRPC Services", "Checks product availability."));
        paths.addPathItem("localhost:9090/InventoryService/UpdateInventory", createGrpcEndpoint("gRPC Services", "Updates product inventory."));

        return paths;
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

    private PathItem createPutEndpoint(String tag, String description, Schema<?> schema) {
        return new PathItem().put(
                new io.swagger.v3.oas.models.Operation()
                        .summary(description)
                        .tags(List.of(tag))
                        .requestBody(new RequestBody().content(getJsonContent(schema)))
                        .responses(getDefaultResponses()));
    }

    private PathItem createDeleteEndpoint(String tag, String description) {
        return new PathItem().delete(
                new io.swagger.v3.oas.models.Operation()
                        .summary(description)
                        .tags(List.of(tag))
                        .responses(getDefaultResponses()));
    }

    private PathItem createGrpcEndpoint(String tag, String description) {
        return new PathItem().post(
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
                .addApiResponse("200", new ApiResponse().description("Successful request"))
                .addApiResponse("400", new ApiResponse().description("Invalid request"))
                .addApiResponse("401", new ApiResponse().description("Unauthorized"))
                .addApiResponse("500", new ApiResponse().description("Internal server error"));
    }

    private Schema<?> getAuthSchema() {
        return new Schema<>()
                .addProperty("username", new StringSchema().example("testuser"))
                .addProperty("password", new StringSchema().example("test123"))
                .addProperty("email", new StringSchema().example("test@example.com"));
    }

    private Schema<?> getProductSchema() {
        return new Schema<>()
                .addProperty("productId", new StringSchema().example("J001"))
                .addProperty("name", new StringSchema().example("Diamond Ring"))
                .addProperty("quantity", new StringSchema().example("2"))
                .addProperty("unitPrice", new StringSchema().example("500"));
    }
}
