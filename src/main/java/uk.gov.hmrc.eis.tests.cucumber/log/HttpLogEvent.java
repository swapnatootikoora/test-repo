package uk.gov.hmrc.eis.tests.cucumber.log;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({
        "logType",
        "url",
        "headers",
        "payload",
        "responseCode"
})
public class HttpLogEvent extends LogEvent {
    private String url;
    private String payload;
    private MultiValueMap<String, String> headers;
    private int responseCode;
    @Override

    public String getLogType() {
        return "HTTP Request";
    }
    @Override
    public String toString() {
        return null;
    }
}
