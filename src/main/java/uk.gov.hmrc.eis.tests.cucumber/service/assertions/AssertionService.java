package uk.gov.hmrc.eis.tests.cucumber.service.assertions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gov.hmrc.eis.tests.cucumber.exception.XmlComparisonException;
import uk.gov.hmrc.eis.tests.cucumber.service.XMLComparisonService;
import uk.gov.hmrc.eis.tests.cucumber.utils.DateUtil;
import uk.gov.hmrc.eis.tests.cucumber.utils.XmlParserUtil;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

@Service
@RequiredArgsConstructor
public class AssertionService {
    private final XmlComparisonService xmlComparisonService;
    private final XmlParserUtil xmlParserUtil;
    public void assertHeadersEquals(Map<String, String> expected, Map<String, String> actual){
        final Map<String, String> actualIgnoreCase = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        actualIgnoreCase.putAll(actual);
        expected.forEach((key, value) -> {
            assertTrue(actualIgnoreCase.containsKey(key),
                    "Expected headers to contain " + key + " but header was missing.");
            assertEquals(value, actualIgnoreCase.get(key),
                    "Expected header " + key + "to equal " + value + " but was " + actualIgnoreCase.get(key));
        });
    }
    public void assertDateFormat(String headerValue, String dateFormat) {
        try {
            DateUtil.parseDateTime(headerValue, dateFormat);
        }
        catch(DateTimeParseException e) {
            fail("Failed to parse date " + headerValue + " with format " + dateFormat + "," + "with exception " + e.getMessage());

        }
        public void assertQueryStringContainsAll(String queryString, Map<String, String> expected){
            assertEquals (
                    Arrays.stream(queryString
                            .split("&"))
                            .map(v -> v.split("="))
                            .collect(Collectors.toMap((s -> s[0], s-> s[1])), expected);
        }
        public void validateSoap11PayloadAgainstSchema(String actualPayload, String schemaName)
        {
            String content = xmlParserUtil.getSoap11Payload(actualPayload);
            xmlComparisonService.validateXmlToSchema(content, schemaName);
        }

        public void validateSoap12PayloadAgainstSchema(String actualPayload, String schemaName)
        {
            String content = xmlParserUtil.getSoap12Payload(actualPayload);
            xmlComparisonService.validateXmlToSchema(content, schemaName);
        }
    }
}