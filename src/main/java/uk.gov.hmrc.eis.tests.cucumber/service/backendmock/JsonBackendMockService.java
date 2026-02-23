package uk.gov.hmrc.eis.tests.cucumber.service.backendmock;



import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;
import uk.gov.hmrc.eis.tests.cucumber.config.properties.MockProperties;
import uk.gov.hmrc.eis.tests.cucumber.http.TRequestResponse;
import uk.gov.hmrc.eis.tests.cucumber.service.PayloadService;

public class JsonBackendMockService extends DefaultBackendMockService {
    public JsonBackendMockService(
            RestTemplate mockRestTemplate,
            MockProperties mockProperties,
            ScenarioContext scenarioContext,
            PayloadService payloadService
    ) {
        super(
                mockRestTemplate,
                mockProperties,
                scenarioContext,
                payloadService
        );
    }

    @Override
    public void executeTRequest() {
        final ResponseEntity<TRequestResponse> response = executeTRequestInternal(TRequestResponse.class);
        final TRequestResponse responseBody = response.getBody();
        if (responseBody != null) {
            responseBody.setStatusCode(response.getStatusCode());
        }
        scenarioContext.setMockTRequestResponse(
                responseBody
        );
    }

}

