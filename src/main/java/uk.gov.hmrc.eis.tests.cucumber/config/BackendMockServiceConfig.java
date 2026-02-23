package uk.gov.hmrc.eis.tests.cucumber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;
import uk.gov.hmrc.eis.tests.cucumber.config.properties.MockProperties;
import uk.gov.hmrc.eis.tests.cucumber.service.PayloadService;
import uk.gov.hmrc.eis.tests.cucumber.service.backendmock.BackendMockService;
import uk.gov.hmrc.eis.tests.cucumber.service.backendmock.DefaultBackendMockService;

import java.beans.BeanProperty;

@Configuration
public class BackendMockServiceConfig {
    @Bean
    public BackendMockService backendMockService(
            RestTemplate mockRestTemplate,
            MockProperties mockProperties,
            ScenarioContext scenarioContext,
            PayloadService payloadService
    ) {
        return new DefaultBackendMockService(
                mockRestTemplate,
                mockProperties,
                scenarioContext,
                payloadService
        );
    }
}