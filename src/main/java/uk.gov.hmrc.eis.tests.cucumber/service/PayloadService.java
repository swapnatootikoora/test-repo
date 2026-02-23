package uk.gov.hmrc.eis.tests.cucumber.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import uk.gov.hmrc.eis.tests.cucumber.config.properties.JourneyProperties;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayloadService {
    private final JourneyProperties journeyProperties;
    private final ResourceLoader resourceLoader;
    private  final Map<String, String> payloads = new HashMap<>();

    public String loadRequestPayload(String payloadName) throws IOException {
        final String fullPath = "classpath:requestPayload/"
                + payloadName.replaceAll("", "_")
                + journeyProperties.getPayloadType().getFileExtension();
        return getPayload(fullPath);
    }
    public String loadResponsePayload(String payloadName) throws IOException {
        final String fullPath = "classpath:responsePayload/"
                + payloadName.replaceAll("", "_")
                + journeyProperties.getPayloadType().getFileExtension();
        return getPayload(fullPath);
    }

    public String getPayload(String fullPath) throws IOException {
        if (payloads.containsKey((fullPath))
         {
            return payloads.get(fullPath);
        }
        else{
            final String fileData = IOUtils.toString(resourceLoader.getResource(fullPath).getInputStream());
            payloads.put(fullPath, fileData);
            return fileData;

        }
    }

}