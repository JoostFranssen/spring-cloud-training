package joffysloffy.worldcaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallEndpoint {
    @Autowired
    private CallService callService;

    @GetMapping(path = "call/{greet}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String call(@PathVariable String greet) {
        callService.setGreet(greet);
        return callService.getHello();
    }

    @GetMapping(path = "info", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getInfo() {
        return callService.getInfo();
    }
}
