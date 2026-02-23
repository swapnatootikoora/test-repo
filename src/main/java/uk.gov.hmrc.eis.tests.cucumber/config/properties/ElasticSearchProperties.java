package uk.gov.hmrc.eis.tests.cucumber.config.properties;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "elastic")
@Data
public class ElasticSearchProperties {
    private String hostname = "localhost";
    private int port = 8080;
}