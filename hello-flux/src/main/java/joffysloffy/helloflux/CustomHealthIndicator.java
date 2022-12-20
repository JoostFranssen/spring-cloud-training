package joffysloffy.helloflux;

import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomHealthIndicator extends AbstractReactiveHealthIndicator {
    private boolean up;

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        up = !up;
        Health health = builder.status(up ? Status.UP : Status.OUT_OF_SERVICE).build();
        return Mono.just(health);
    }
}
