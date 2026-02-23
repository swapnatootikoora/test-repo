package uk.gov.hmrc.eis.tests.cucumber.http;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TRequestResponse {
    private HttpStatus statusCode;
    private Map<String, String> headers;
    private String requestPath;
    private String queryString;
    private String Content;
}
