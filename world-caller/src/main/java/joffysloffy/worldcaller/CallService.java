package joffysloffy.worldcaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {
    private static final Logger log = LoggerFactory.getLogger(CallService.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    public String getInfo() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("info-breaker");
        return circuitBreaker.run(
                () -> restTemplate.getForObject("http://helloworld", String.class),
                t -> "**********:????"
        );
    }

    public String getHello() {
        URI uri = getUrl("helloworld").orElseThrow();
        return restTemplate.getForObject(uri + "/hello", String.class);
    }

    private Optional<URI> getUrl(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        log.info("Instances: " + instances);
        return instances.stream().map(ServiceInstance::getUri).findAny();
    }

    public void setGreet(String name) {
        Greet greet = new Greet(name);
        log.info("Greet: " + greet.getName());
        RestTemplate restTemplate = new RestTemplate();
        try {
            String json = objectMapper.writeValueAsString(greet);
            log.info("Posting: " + json);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            URI uri = getUrl("helloworld").orElseThrow();
            restTemplate.postForLocation(uri + "/hello", request);

        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
