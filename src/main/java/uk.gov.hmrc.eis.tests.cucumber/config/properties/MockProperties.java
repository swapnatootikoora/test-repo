package uk.gov.hmrc.eis.tests.cucumber.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mock")
@Data
public class MockProperties {
    private String url;
    private String path;
    private String validSoapPayload = "";
    private boolean soap = false;
    private String soapAction = "";
    private int tRequestMaxWaitMillis = 5000;
    private int tRequestPollIntervalMillis = 500;
   private HttpMethod method = HttpMethod.POST;
}