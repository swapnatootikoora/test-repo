package uk.gov.hmrc.eis.tests.cucumber.stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;
import uk.gov.hmrc.eis.tests.cucumber.service.DataSubstitutionService;
import uk.gov.hmrc.eis.tests.cucumber.service.PayloadService;
import uk.gov.hmrc.eis.tests.cucumber.service.assertions.AssertionService;
import uk.gov.hmrc.eis.tests.cucumber.service.assertions.TRequestAssertionService;
import uk.gov.hmrc.eis.tests.cucumber.service.assertions.TracePointAssertionService;

import java.util.HashMap;
import java.util.stream.Collectors;

public class BackendStepDefinitions implements En {
    public BackendStepDefinitions(
            ScenarioContext scenarioContext,
            TRequestAssertionService tRequestAssertionService,
            DataSubstitutionService dataSubstitutionService)
    {
        Given("the backend mock scenario {string)", (String mockSceanrio) ->
                scenarioContext.getJourneyRequestHeaders().put("mdg_test_scenario", mockSceanrio)
        );
        //TODO Mark as deprected in favour of XmlStepDefinition's "the backend should receive the xml payload"

        Then("verify the backend received the payload {string)", (String payloadName) ->
                tRequestAssertionService.assertBackendReceiveXmlPayload(payloadName));

        Then("verify the backend received the payload {string) with the following values:", (String payloadName, DataTable values) ->
                tRequestAssertionService.assertBackendReceiveXmlPayload(payloadName, dataSubstitutionService.processDataTable(values)));

        Then("verify the backend received all the headers sent to the journey", () ->
                tRequestAssertionService.assertBackendReceivedHeaders(scenarioContext.getJourneyRequestHeaders()));
        Then("verify the backend should receive all the headers sent to the journey", () ->
                tRequestAssertionService.assertBackendReceivedHeaders(scenarioContext.getJourneyRequestHeaders()));
        Then("verify the backend should received the header {string) in the date format {string}",
                tRequestAssertionService::assertBackendReceivedDateHeaderWithFormat);
        Then("the backend should receive the header {string) header in the date format {string}",
                tRequestAssertionService::assertBackendReceivedDateHeaderWithFormat);

        Then("verify that the backend received the following query string parameters:", (DataTable parameters) ->

                tRequestAssertionService.assertBackendReceivedQueryParameters(parameters.asMap()));

        Then("the backend should receive the following query string parameters:", (DataTable parameters) ->

                tRequestAssertionService.assertBackendReceivedQueryParameters(parameters.asMap()));

        Then("verify that the backend received a request to the path {string}",

                tRequestAssertionService::assertBackendReceivedRequestToPath);

        Then("the backend should receive a request to the path {string}",

                tRequestAssertionService::assertBackendReceivedRequestToPath);

        Then("the backend should not receive a request",

                tRequestAssertionService::assertBackendDidNotReceiveRequest);

        Then("the payload in the SOAP 1.1 Body sent to the backend validates against the schema {string}",

                tRequestAssertionService::assertBackendSoap12BodyValidatesAgainstSchema);
    }
}