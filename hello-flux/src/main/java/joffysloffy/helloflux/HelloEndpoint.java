package joffysloffy.helloflux;

import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RefreshScope
public class HelloEndpoint {
    @Autowired
    private FluxProducer fluxProducer;

    @Autowired
    private Counter requestCounter;

    @Value("${hello.flux.custom:}")
    private String custom;

    @Value("${server.port}")
    private int port;

    @Value("${spring.application.name}")
    private String name;

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

    @GetMapping(path = "custom", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> property() {
        return Mono.just(custom);
    }

    @GetMapping(path = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> info() {
        return Mono.just(name + ":" + port);
    }
}
