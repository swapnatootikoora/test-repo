package uk.gov.hmrc.eis.tests.cucumber.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "journey")
@Data
public class JourneyProperties {
    private String url;
    private String path;
    private String additionalPath;
    private PayloadType payloadType = PayloadType.XML;
    public void setAdditionalPath(String additionalPath) {
        this.additionalPath = additionalPath;
    }
}