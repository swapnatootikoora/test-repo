package uk.gov.hmrc.eis.tests.cucumber.stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.springframework.http.HttpMethod;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;
import uk.gov.hmrc.eis.tests.cucumber.service.DataSubstitutionService;
import uk.gov.hmrc.eis.tests.cucumber.service.HttpService;
import uk.gov.hmrc.eis.tests.cucumber.service.PayloadService;
import uk.gov.hmrc.eis.tests.cucumber.service.assertions.AssertionService;
import uk.gov.hmrc.eis.tests.cucumber.service.assertions.TracePointAssertionService;
import uk.gov.hmrc.eis.tests.cucumber.utils.TracePoint;

import java.util.HashMap;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommonStepDefinitions implements En {
    public CommonStepDefinitions(
            ScenarioContext testContext,
            HttpService httpService,
            DataSubstitutionService dataSubstitutionService,
            PayloadService payloadService,
            AssertionService assertionService,
            TracePointAssertionService tracePointAssertionService)
    {
        Given("the following headers:", (DataTable headers) ->
                testContext.setJourneyRequestHeaders(dataSubstitutionService.processDataTable(headers))
        );
        Given("the following path variables:", (DataTable pathVariables) ->
                testContext.setJourneyPathVariables(new HashMap<>(pathVariables.asMap()))
        );
        Given("the following query parameters:", (DataTable parameters) ->
                testContext.setJourneyQueryParameters(dataSubstitutionService.processDataTable(parameters))
        );

        Given("the payload {string}", (String  payloadName) ->
                testContext.setJourneyRequestPayload(payloadService.loadRequestPayload(payloadName))
        );

        When("a {sring} request is sent to the journey", (String method) ->
                httpService.executeHttpRequests(HttpMethod.valueOf(method))
        );

        Then("verify a {int} response is returned from the journey", (Integer responseCode) ->
                assertEquals(responseCode, testContext.getJourneyResponse().getStatusCode().value()));


        Then("verify a {int} response should be returned from the journey", (Integer responseCode) ->
                assertEquals(responseCode, testContext.getJourneyResponse().getStatusCode().value()));

        Then("the response should have no body", () ->
                assertNull(testContext.getJourneyResponse().getBody()));

        Then("verify that the following headers are returned:", (DataTable headers) ->
assertionService.assertDateFormat(
        dataSubstitutionService.processDataTable(headers),
        testContext.getJourneyResponse().getHeaders().toSingleValueMap())
);

        Then("the following headers should be returned:", (DataTable headers) ->
                assertionService.assertHeadersEqual(
                        dataSubstitutionService.processDataTable(headers),
                        testContext.getJourneyResponse().getHeaders().toSingleValueMap())
        );
        Then("all relevant trace points are logged for the journey", () -> {
                tracePointAssertionService.assertHasMircroserviceTracePoints(testContext.getCorrleationId());
                tracePointAssertionService.assertHasWAFTracePoints(testContext.getCorrleationId());
    });

        Then("WAF layer and microservice trace points should be logged for the journey", () -> {
            tracePointAssertionService.assertHasMircroserviceTracePoints(testContext.getCorrleationId());
            tracePointAssertionService.assertHasWAFTracePoints(testContext.getCorrleationId());
        });

        Then("only WAF layer trace points are logged for the journey", () -> {
            tracePointAssertionService.assertHasMircroserviceTracePoints(testContext.getCorrleationId());
            tracePointAssertionService.assertHasWAFTracePoints(testContext.getCorrleationId());
        });

        Then("only WAF layer trace points should be logged for the journey", () -> {
            tracePointAssertionService.assertHasMircroserviceTracePoints(testContext.getCorrleationId());
            tracePointAssertionService.assertHasWAFTracePoints(testContext.getCorrleationId());
        });

        Then("verify microservice trace points are logged for the journey", () -> {
            tracePointAssertionService.assertHasMircroserviceTracePoints(testContext.getCorrleationId());
        });
        Then("verify the following trace points are logged for the journey:", (DataTable tracePoints) -> {
            tracePointAssertionService.assertHasTracePoints(
                    testContext.getCorrleationId(),
                    tracePoints.asList()
                            .stream()
                            .map(Tracepoint::valueOf)
                            .collect(Collectors.toList())
            );

            Then("the following trace points should be logged for the journey:", (DataTable tracePoints) -> {
                tracePointAssertionService.assertHasTracePoints(
                        testContext.getCorrleationId(),
                        tracePoints.asList()
                                .stream()
                                .map(Tracepoint::valueOf)
                                .collect(Collectors.toList())
                );

        });

}








