package joffysloffy.helloflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class HelloEndpoint {
    @Autowired
    private FluxProducer fluxProducer;

    @GetMapping(path = "hello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamHello() {
        return fluxProducer.produce();
    }

    @GetMapping(path = "books", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> streamBooks() {
        return fluxProducer.books();
    }
}
