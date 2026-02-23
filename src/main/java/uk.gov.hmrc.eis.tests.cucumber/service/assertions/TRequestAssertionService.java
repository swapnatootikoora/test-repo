package uk.gov.hmrc.eis.tests.cucumber.service.assertions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.https.HttpService;

import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;
import uk.gov.hmrc.eis.tests.cucumber.exception.BackendMockException;
import uk.gov.hmrc.eis.tests.cucumber.http.TRequestResponse;
import uk.gov.hmrc.eis.tests.cucumber.service.JsonManipulationService;
import uk.gov.hmrc.eis.tests.cucumber.service.backendmock.BackendMockService;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Service
@RequiredArgsConstructor
public class TRequestAssertionService {
    private final ScenarioContext scenarioContext;
    private final BackendMockService backendMockService;
    private final AssertionService assertionService;
    private XmlAssertionService xmlAssertionService;
    private JsonAssertionService jsonAssertionService;
    private JsonManipulationService jsonManipulationService;

    public void assertBackendReceiveXmlPayload(String payloadName) throws Exception {
        xmlAssertionService.assertXmlPayloadEqual(
                retrieveTRequestAndCheck404().getContent(),
                payloadName
        );
    }
    public void assertBackendReceiveXmlPayload(String payloadName, Map<String, String> repleacementValues) throws Exception {
        xmlAssertionService.assertXmlPayloadEqual(
                retrieveTRequestAndCheck404().getContent(),
                payloadName,
                repleacementValues
        );
    }
    public void assertBackendReceiveJsonPayload(String payloadName)  {
        jsonAssertionService.assertJsonStringsEqual(
                retrieveTRequestAndCheck404().getContent(),
                payloadName
        );
    }

    public void assertBackendReceiveJsonPayload(String payloadName, Map<String, String> repleacementValues)  {
       String replacePayload = jsonManipulationService.setNodes(payloadName, repleacementValues);

        jsonAssertionService.assertJsonEqual(
                retrieveTRequestAndCheck404().getContent(),
                replacePayload
        );
    }
    public void assertBackendReceivedHeaders(Map<String, String> expected)
    {
        assertionService.assertHeadersEqual(
             expected,
             retrieveTRequestAndCheck404().getHeaders()
        );
    }
    public void assertBackendReceivedQueryParameters(Map<String, String> expected)
    {
        assertionService.assertQueryStringContainsAll(
                retrieveTRequestAndCheck404().getQueryString(),
                expected
        );
    }

    public void assertBackendReceivedRequestToPath(String path)
    {
        assertEquals(
                path,
                retrieveTRequestAndCheck404().getRequestPath()

        );
    }

    public void assertBackendDidNotReceiveRequest()
    {
        assertEquals(
                HttpStatus.NOT_FOUND,
                retrieveTRequest().getStatusCode()

        );
    }
public void assertBackendReceiveDateHeaderWithFormat(String headerName,String dateFormat){
    Map<String, String> backendHeaders = retrieveTRequestAndCheck404().getHeaders();
    assertTrue(backendHeaders.containsKey
            (headerName),
            "Expected header to contain " + headerName + " but header was missing");
    assertionService.assertDateFormat(backendHeaders.get(headerName), dateFormat);

    }


private TRequestResponse retrieveTRequest() {
    if(scenarioContext.getMockTRequestResponse() == null)
    {
    backendMockService.executeTRequest();
    }
    if(scenarioContext.getMockTRequestResponse() == null)
    {
        throw new BackendMockException("TRequest body from the mock is null");
    }
    return scenarioContext.getMockTRequestResponse();
}
private TRequestResponse retrieveTRequestAndCheck404(){
    retrieveTRequest();
    assertEquals(
            HttpStatus.OK,
            scenarioContext.getMockTRequestResponse().getStatusCode(),
            "TRequest did not return ok, returned " +
                    scenarioContext.getMockTRequestResponse().getStatusCode().value()
    );
    return scenarioContext.getMockTRequestResponse();
    }

    public void assertBackendSoap11BodyValidatesAgainstSchema(String schemaName){
        assertionService.validateSoap11PayloadAgainstSchema(retrieveTRequestAndCheck404().getContent(), schemaName);

    }
    public void assertBackendSoap12BodyValidatesAgainstSchema(String schemaName){
        assertionService.validateSoap12PayloadAgainstSchema(retrieveTRequestAndCheck404().getContent(), schemaName);

    }
}


