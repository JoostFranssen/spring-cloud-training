package joffysloffy.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldEndpoint {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldEndpoint.class);


    @Autowired
    private GreetPersistence greetPersistence;

    @GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    String getGreeting() {
        log.debug("Incoming GET request");
        return "Hello " + greetPersistence.getGreet() + "!";
    }

    @PostMapping(path = "hello", consumes = MediaType.APPLICATION_JSON_VALUE)
    void greet(@RequestBody Greet greet) {
        log.debug("Updating greet to " + greet.getName());
        greetPersistence.setGreet(greet.getName());
    }
}
