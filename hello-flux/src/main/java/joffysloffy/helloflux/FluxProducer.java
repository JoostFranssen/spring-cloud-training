package joffysloffy.helloflux;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class FluxProducer {
    public Flux<String> produce() {
        return Flux.interval(Duration.of(1, ChronoUnit.SECONDS))
                .map(d -> "Flux emitted at " + LocalDateTime.now());
    }

    public Flux<Book> books() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(d -> new Book("Author", "Book Part " + d));
    }
}
