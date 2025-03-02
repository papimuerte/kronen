package com.graphql;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    private static final Logger logger = LoggerFactory.getLogger(StripeService.class);

    public StripeService() {
        Stripe.apiKey = "sk_test_51JlxjKIsIcjwovka9A1bd98rPcNPhsPb5EigmVUCRkvopovGq6fDTfYKhFfADyX2x0cPA3PYkIYqDUR11qzWOjr800QNTgN27m";
        logger.info("Stripe API Key set successfully");
    }

    public PaymentIntent createPayment(Float amount, String currency, String paymentMethodId) throws StripeException {
        logger.info("Processing Stripe payment: Amount = {}, Currency = {}, PaymentMethodId = {}", amount, currency, paymentMethodId);

        if (amount == null || currency == null || paymentMethodId == null) {
            logger.error("Invalid Stripe request: Missing parameters.");
            throw new IllegalArgumentException("Amount, currency, and paymentMethodId cannot be null.");
        }

        try {
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount * 100)) // Convert to cents
                    .setCurrency(currency)
                    .setPaymentMethod(paymentMethodId) // Attach payment method
                    .setConfirm(true) // Automatically confirm payment
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            logger.info("Stripe PaymentIntent created: ID = {}, Status = {}", paymentIntent.getId(), paymentIntent.getStatus());

            return paymentIntent;
        } catch (StripeException e) {
            logger.error("Stripe API Error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process Stripe payment: " + e.getMessage(), e);
        }
    }
}
