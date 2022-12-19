package joffysloffy.helloworld;

import org.springframework.stereotype.Service;

@Service
public class GreetPersistence {
    private String greet = "World";

    public String getGreet() {
        return greet;
    }

    public void setGreet(String greet) {
        this.greet = greet;
    }
}
