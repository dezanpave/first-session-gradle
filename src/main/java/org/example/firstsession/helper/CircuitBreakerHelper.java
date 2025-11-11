package org.example.firstsession.helper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CircuitBreakerHelper {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerHelper.class);


    public CircuitBreakerHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "thirdPartyApi", fallbackMethod = "apiFallback")
    public <T> T callExternalApi(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    public <T> T apiFallback(String url, Class<T> responseType, Exception ex) {
        logger.error("Circuit breaker triggered for URL: {} | Response type: {} | Error: {}",
                url, responseType.getSimpleName(), ex.getMessage(), ex);
        logger.warn("Returning null as fallback response. Service is temporarily unavailable.");
        return null;
    }
}
