package joffysloffy.worldcaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallService {
    @Value("${helloservice.url}")
    private String url;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(CallService.class);

    public String getHello() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url + "/hello", String.class);
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
            restTemplate.postForLocation(url + "/hello", request);

        } catch(JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
