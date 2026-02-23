package uk.gov.hmrc.eis.tests.cucumber.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test")
@Data
public class TestProperties {

    private String traceEventsIndex;
    private int tracePointQueryMaxWaitSeconds = 15;
    private int tracePointQueryIntervalSeconds = 1;
}