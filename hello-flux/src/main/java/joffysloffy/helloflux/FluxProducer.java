package joffysloffy.helloflux;

import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class FluxProducer {
    @Autowired
    private Timer fluxTimer;

    public Flux<String> produce() {
        return Flux.interval(Duration.of(1, ChronoUnit.SECONDS))
                .map(d -> "Flux emitted at " + LocalDateTime.now() + " on " + Thread.currentThread().getName())
                .doOnNext(s -> fluxTimer.record(() -> {}));
    }

    public Flux<Book> books() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(d -> new Book("Author", "Book Part " + d));
    }
}
