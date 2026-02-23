package uk.gov.hmrc.eis.tests.cucumber.stepDefinitions;

import io.cucumber.java8.En;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;
import uk.gov.hmrc.eis.tests.cucumber.service.assertions.TRequestAssertionService;
import uk.gov.hmrc.eis.tests.cucumber.utils.XmlParseUtil;


public class FrontStepDefinitions implements En {
    public FrontStepDefinitions(
            ScenarioContext testContext,
            TRequestAssertionService tRequestAssertionService) {
        Given("the header {string} has the value {string}", (String headerName, String headerVal) -> {
            testContext.getJourneyRequestHeaders().remove(headerName);
            testContext.getJourneyRequestHeaders().put(headerName, headerVal);

        });

        Given("the {string} header is {string}", (String headerName, String headerVal) -> {
            testContext.getJourneyRequestHeaders().remove(headerName);
            testContext.getJourneyRequestHeaders().put(headerName, headerVal);

        });
        Given("the path variable {string} has the value {string}", (String name, String value) ->
                testContext.getJourneyPathVariables().put(name, value));
        Given("the path variable {string} is {string}", (String name, String value) ->
                testContext.getJourneyPathVariables().put(name, value));
        Given("the path variable {string} is empty", (String name) ->
                testContext.getJourneyPathVariables().put(name, "$remove"));

        Given("the {string} header is removed from the headers", (String headerName) ->
                testContext.getJourneyRequestHeaders().remove(headerName));

        Given("the {string} header is removed/missing", (String headerName) ->
                testContext.getJourneyRequestHeaders().remove(headerName));

        Given("the payload property {string} has the value {string}", (String propName, String propVal) ->
        {
            String currentRequestPayload = testContext.getJourneyRequestPayload();
            String rebuiltXmlPayload = XmlParserUtil.replaceAndRebuildXml(currentRequestPayload, propName, propVal);
            testContext.setJourneyRequestPayload(rebuiltXmlPayload);
        });

        Then("verify backend did not receive the request", tRequestAssertionService::assertBackendDidNotReceiveRequest);
    }
}












