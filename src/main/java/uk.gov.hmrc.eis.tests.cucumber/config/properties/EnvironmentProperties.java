package uk.gov.hmrc.eis.tests.cucumber.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "environment")
@Data
public class EnvironmentProperties {
    private String url;
    private String project;
    private String namespace;
    private String cluster;
    private String clusterId;
    private String journey;
    private String stage;
}