package uk.gov.hmrc.eis.tests.cucumber.service.assertions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import uk.gov.hmrc.eis.tests.cucumber.service.PayloadService;
import uk.gov.hmrc.eis.tests.cucumber.service.XMLComparisonService;
import uk.gov.hmrc.eis.tests.cucumber.utils.XmlParseUtil;

import java.util.Map;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class XmlAssertionService {
    private final PayloadService payloadService;
    private final XMLComparisonService xmlComparisonService;
    private final AssertionService assertionService;

    public void assertXmlPayloadEqual(String actualPayload, String expectedPayloadName) throws Exception {
        assertXmlPayloadEqual(actualPayload, expectedPayloadName, Collections.emptyMap());
    }

    public void assertXmlPayloadEqual(
            String actualPayload,
            String expectedPayloadName,
            Map<string, String> replacementValues) throws Exception{
    String expectedXml = XmlParseUtil.replaceAndRebuildXml(
            payloadService.loadRequestPayload(expectedPayloadName),replacementValues
    );
    xmlComparisonService.assertEqual(actualPayload,expectedXml);
    }
}





