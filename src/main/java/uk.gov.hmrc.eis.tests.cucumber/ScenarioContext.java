package uk.gov.hmrc.eis.tests.cucumber;

import io.cucumber.java8.Scenario;
import io.cucumber.spring.ScenarioScope;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.Maputils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uk.gov.hmrc.eis.tests.cucumber.http.TRequestResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@ScenarioScope
@Getter
@Setter
public class ScenarioContext {
    private Scenario scenario;
    @Getter(AccessLevel.NONE)
    private String corrleationId;

    private Map<String, String> journeyRequestHeaders;
    private Map<String, String> journeyPathVariables;
    private Map<String, String> journeyQueryParameters;
private String journeyRequestPayload;

private ResponseEntity<String> journeyResponse
        private TRequestResponse mockTRequestResponse;

public ScenarioContext() {
    journeyRequestHeaders = new HashMap<>();
    journeyPathVariables = new HashMap<>();
    journeyQueryParameters = new HashMap<>();
    corrleationId = UUID.randomUUID().toString();
}

public String getCorrleationId() {
    return MapUtils.getString(journeyRequestHeaders, "X-Correlation-ID", corrleationId);
}

}


