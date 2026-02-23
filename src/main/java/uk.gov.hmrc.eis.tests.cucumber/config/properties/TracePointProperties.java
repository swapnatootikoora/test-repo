package uk.gov.hmrc.eis.tests.cucumber.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tracepoints")
@Data
public class TracePointProperties {

    private boolean assertionEnabled = true;
}