package uk.gov.hmrc.eis.tests.cucumber.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import uk.gov.hmrc.eis.tests.cucumber.log.HttpLogEvent;
import uk.gov.hmrc.eis.tests.cucumber.service.LogService;
import java.io.IOException

@RequiredArgsConstructor
public class HttpLogInterceptor implements ClientHttpRequestInterceptor
