package uk.gov.hmrc.eis.tests.cucumber.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;

/* Service for use by other services to simplify deserializing JSON strings */

@Service
@Slf4j
public class JsonDeserializationService {
    private final ObjectMapper objectMapper;

    public JsonDeserializationService() {
        objectMapper = new ObjectMapper();
    }
    /* Parse a serialized json string and return it as a JSONObject object
    @param jsonString A string containing serialized json
    @return the input json as a final org.json.JSONObject object
     */

    public JSObject jsonStringToJSONObject_NonECMA404Compliant(final String jsonString) throws JsonDeserializationServiceException {
        final JSONObject result;
        try {
            result = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new JsonDeserializationServiceException(MessageFormat.format(
                    "Unable to parse input string [{0}] into JSONObject",
                    jsonString), e);
        }
        return result;
    }

    public JsonNode jsonStringToJsonNode(final String jsonString) throws JsonDeserializationServiceException {
        final JsonNode result;
        try {
            result = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new JsonDeserializationServiceException(MessageFormat.format()
                    "unable to parse input string [{0}] into JsonNode",
                    jsonString, e);
        }
        return result;
    }

    public static class JsonDeserializationServiceException extends Exception {
        private JsonDeserializationServiceException(String s, Exception e) {
            super(s, e);
        }
    }
}
