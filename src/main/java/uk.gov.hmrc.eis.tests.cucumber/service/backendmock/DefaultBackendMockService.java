package uk.gov.hmrc.eis.tests.cucumber.service.backendmock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.core.ConditionTimeoutException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;
import uk.gov.hmrc.eis.tests.cucumber.exception.BackendMockException;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;
import uk.gov.hmrc.eis.tests.cucumber.config.properties.MockProperties;
import uk.gov.hmrc.eis.tests.cucumber.http.TRequestResponse;
import uk.gov.hmrc.eis.tests.cucumber.service.PayloadService;

import java.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static uk.gov.hmrc.eis.tests.cucumber.CucumberConstants.*;

@RequiredArgsConstructor
@Slf4j
public class DefaultBackendMockService implements BackendMockService {
    protected final RestTemplate mockRestTemplate;
    protected final MockProperties mockProperties;
    protected final ScenarioContext scenarioContext;
    protected final PayloadService payloadService;
    private String validSoapPayload;

    private Duration tRequestMaxWait;
    private Duration tRequestPollInterval;

    @PostConstruct
    public void init() {
        tRequestMaxWait = Duration.ofMillis(mockProperties.getTRequestMaxWaitMillis());
        tRequestPollInterval = Duration.ofMillis(mockProperties.getTRequestPollIntervalMillis());
    }

    @Override

    public <T> ResponseEntity<T> executeRequest(
            HttpMethod method,
            MultiValueMap<String, String> headers,
            Class<T> type
    ) {
        return executeRequest(method, headers, null, type);
    }

    @Override

    public <T> ResponseEntity<T> executeRequest(
            HttpMethod method,
            MultiValueMap<String, String> headers,
            String payload,
            Class<T> type
    ) {
        try {
            return mockRestTemplate.exchange(
                    mockProperties.getUrl() + "/" + mockProperties.getPath(),
                    method,
                    new HttpEntity<>(
                            payload,
                            headers
                    ),
                    type
            );
        } catch (UnknownContentTypeException e) {
            // this is only for the mock war throwing 404 error.
            return ResponseEntity.status(e.getRawStatusCode()).body(null);
        }
    }

    protected <T> ResponseEntity<T> executeTRequestInternal(Class<T> responseType) {
        final MultiValueMap<String, String> mockRequestHeaders = new LinkedMultiValueMap<>();
        mockRequestHeaders.add(CORRLEATION_ID_HEADER, scenarioContext.getCorrleationId());
        mockRequestHeaders.add(MOCK_SCENARIO_HEADER, MOCK_TREQUEST_SCENARIO);

        if (mockProperties.isSoap()) {
            mockRequestHeaders.add(SOAP_ACTION_HEADER, mockProperties.getSoapAction());
            if (validSoapPayload == null) {
                try {
                    validSoapPayload = payloadService.loadRequestPayload(
                            mockProperties.getValidSoapPayload());
                } catch (IOException e) {
                    throw new BackendMockException(
                            "Could not load valid soap request payload configured under mock.validSoapPayload. This "
                                    + "should be the name of a valid payload file, as it's required that SOAP request have a valid payload.", e
                    );
                }
            }
        }
        try {
            return await()
                    .atMost(tRequestMaxWait)
                    .pollInterval(tRequestPollInterval)
                    .until(
                            () -> executeRequest(
                                    mockProperties.getMethod()
                                    , mockRequestHeaders,
                                    validSoapPayload,
                                    responseType
                            ),
                            response -> response.getBody() != null && response.getStatusCode().is2xxSuccessful()
                    );

        } catch (ConditionTimeoutException e) {
            throw new BackendMockException("TRequest returned an error.", e);
        }
    }

    @Override
    public void executeTRequest() {
        final ResponseEntity<String> response = executeTRequestInternal(String.class);
        scenarioContext.setMockTRequestResponse(
                new TRequestResponse()
                        .setStatusCode(response.getStatusCode())
                        .setHeaders(response.getHeaders().toStringValueMap())
                        .setContent(response.getBody())
        );
    }
}
    }
}


