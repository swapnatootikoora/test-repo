package uk.gov.hmrc.eis.tests.cucumber.service.assertions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.*;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONassert;
import org.skyscreamer.jsonassert.JSONComareMode;
import org.springframework.stereotype.Service;
import uk.gov.hmrc.eis.tests.cucumber.service.JsonDeserialzationService;

@Service
@Slf4j
@RequiredargsConstructor
public class JsonAssertionService {
    private final JsonDeserializationService jsonDeserializationService;
    private final JSONAssertWrapper jsonAssert;
    /* Asserts that the parameters represent a JSON object with an identical set of keys with arrays holding identical
    values is identical order.
     */

    public void assertJsonStringsEqual(final String expected, final String actual) {
        assertJsonStringsWithMode(expected, actual, JSONCompareMode.STRICT);
    }
}
/* Asserts that the parameters represent a JSON object with an identical set of keys with arrays holding identical
    values is any order.
     */

public void assertJsonStringsUnorderedEqual(final String expected, final String actual) {
    assertJsonStringsWithMode(expected, actual, JSONCompareMode.NON_EXTENSIBLE);
}

/* Asserts that the parameters represent a JSON object with all keys present in the expected
 parameter , holding equal value with arrays holding identical
    values is any order.
     */

public void assertJsonStringsExtensibleUnorderedEqual(final String expected, final String actual) {
    assertJsonStringsWithMode(expected, actual, JSONCompareMode.LENIENT);
}

/* Asserts that the actual parameters represent a JSON object with all keys present in the expected
parameter , holding equal value
        */

public void assertJsonStringsExtensibleEqual(final String expected, final String actual) {
    assertJsonStringsWithMode(expected, actual, JSONCompareMode.STRICT_ORDER);
}

public void assertJsonStringsWithMode(final String expected, final String actual, final JSONCompareMode mode) {
    Assertions.assertAll (
            "Input validation",
            () -> assertValidJsonNode(expected),
            () -> assertValidJsonNode(actual));
    try
    {
        jsonAssert.assertEquals(expected, actual, mode);
    }
    catch(JSONException e) {
        //we do not expect this to occur - we've validated the inputs.
        log.error("unexpected JSONException from JSONAssert", e);
        throw new JsonAssertionServiceException("Unexpected JSONException from JSONAssert", e);
    }
    }

/* Asserts that the provided string represents a valid JSON object as handled by jackson's ObjectMapper.
@param input
 */

public void assertValidJSONNode(final String input) {
    Assertions.assertDoesNotThrow(() -> jsonDeserializationService.jsonStringToJsonNode(input));
}
public void assertParseableToJSONObject(final String input) {
    Assertions.assertDoesNotThrow(() -> jsonDeserializationService.jsonStringToJSONObject_NonECMA404Compliant(input));
}

public static class  JsonAssertionServiceException extends RuntimeException {
    private JsonAssertionServiceException(String s, Exception e)
    {
        super(s,e);
    }
}
@Service
public static class JSONAssertWrapper {
    public void assertEquals(
            final String expectedStr,
            final String actualStr,
            final JSONCompareMode compareMode)
        throws JSONException
    {
        JSONAssert.assertEquals(expectedStr,actualStr,compareMode);
    }
}
}

