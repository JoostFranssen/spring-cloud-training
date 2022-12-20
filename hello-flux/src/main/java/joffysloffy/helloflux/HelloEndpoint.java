package joffysloffy.helloflux;

import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class HelloEndpoint {
    @Autowired
    private FluxProducer fluxProducer;

    @Autowired
    private Counter requestCounter;

    @GetMapping(path = "hello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamHello() {
        requestCounter.increment();
        return fluxProducer.produce();
    }

    @GetMapping(path = "books", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> streamBooks() {
        requestCounter.increment();
        return fluxProducer.books();
    }
}
