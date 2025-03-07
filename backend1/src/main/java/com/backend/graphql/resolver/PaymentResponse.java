package com.backend.graphql.resolver;

public class PaymentResponse {
    private String id;
    private String status;
    private String clientSecret;

    public PaymentResponse(String id, String status, String clientSecret) {
        this.id = id;
        this.status = status;
        this.clientSecret = clientSecret;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
