package joffysloffy.helloflux;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeterConfiguration {
    private static final String REQUEST_COUNTER = "counter.requests";
    private static final String FLUX_TIMER = "timer.flux";

    @Bean
    public Counter requestCounter(MeterRegistry meterRegistry) {
        return meterRegistry.counter(REQUEST_COUNTER);
    }

    @Bean
    public Timer fluxTimer(MeterRegistry meterRegistry) {
        return meterRegistry.timer(FLUX_TIMER);
    }
}
