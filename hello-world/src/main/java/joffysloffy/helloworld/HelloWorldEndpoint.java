package joffysloffy.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class HelloWorldEndpoint {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldEndpoint.class);

    @Value("${spring.application.name}")
    private String name;

    @Autowired
    private ServerInfo serverInfo;

    @Autowired
    private GreetPersistence greetPersistence;

    private final Random random = new Random();

    @GetMapping(path = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getInfo() {
        if(random.nextBoolean()) {
            try {
                Thread.sleep(5000);
            } catch(InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }
        return name + ":" + serverInfo.getPort();
    }

    @GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getGreeting() {
        log.debug("Incoming GET request");
        return "Hello " + greetPersistence.getGreet() + "!";
    }

    @PostMapping(path = "hello", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void greet(@RequestBody Greet greet) {
        log.debug("Updating greet to " + greet.getName());
        greetPersistence.setGreet(greet.getName());
    }
}
