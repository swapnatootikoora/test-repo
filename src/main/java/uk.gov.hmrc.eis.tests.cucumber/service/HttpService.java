package uk.gov.hmrc.eis.tests.cucumber.service;

import io.cucumber.datatable.DataTable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UriComponentsBuilder;
import uk.gov.hmrc.eis.tests.cucumber.config.properties.JourneyProperties;
import uk.gov.hmrc.eis.tests.cucumber.utils.CollectionUtil;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;

@Service
@RequiredArgsConstructor
public class HttpService {
    private final ScenarioContext testContext;
    private  final RestTemplate journeyRestTemplate;
    private final JourneyProperties journeyProperties;
    private final DataSubstitutionService dataSubstitutionService;

    public void executeHttpRequests(HttpMethod method) {
        testContext.setJourneyResponse(executeJourneyHttpRequest(method));
    }
    public ResponseEntity<String> executeJourneyHttpRequest(HttpMethod method) {
        final UriComponentsBuilder urlBuilder = UriComponentBuilder.fromHttpUrl(
                journeyProperties.getUrl() + "/" +
                        journeyProperties.getPath()
        );

        if (journeyProperties.getAdditionalPath() != null) {
            urlBuilder.path(
                    dataSubstitutionService.substititeVariables(
                            journeyProperties.getAdditionalPath(),
                            testContext.getJourneyPathVariables()
                    )
            );
        }
        testContext.getJourneyQueryParameters().forEach(urlBuilder::queryParam);

        return journeyRestTemplate.exchange(
                urlBuilder.toUriString(),
                method,
                new HttpEntity<>(
                        testContext.getJourneyRequestPayload(),
                        CollectionUtils.toMultiValueMap(
                                CollectionUtil.toMultiValueMap(testContext.getJourneyRequestHeaders())
                        )
                ),
                String.class
        );
    }
    }
}