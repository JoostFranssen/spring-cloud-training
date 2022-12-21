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
                log.info("Going to sleep...");
                Thread.sleep(5000);
                log.info("Slept for 5s.");
            } catch(InterruptedException ignore) {
                log.info("Sleep was interrupted!");
                Thread.currentThread().interrupt();
            }
        }
        String info = name + ":" + serverInfo.getPort();
        log.info("Return info: " + info);
        return info;
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
