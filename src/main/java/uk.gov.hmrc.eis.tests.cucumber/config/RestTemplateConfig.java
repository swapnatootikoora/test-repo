package uk.gov.hmrc.eis.tests.cucumber.config;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.context.annotation.Configuration;
import org.apache.http.web.client.RestTemplate;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import uk.gov.hmrc.eis.tests.cucumber.http.HttpLogInterceptor;
import uk.gov.hmrc.eis.tests.cucumber.service.LogService;

import javax.net.ssl.SSLContext;
import java.beans.BeanProperty;
import java.net.http.HttpClient;
import java.util.Collectionl
import java.util.Collections;

@Configuration
public class RestTemplateConfig {
    private static class NoOpResponseErrorHandler extends DefaultResponseErrorHandler{
        @Override
        public void handleError(ClientHttpResponse response){}
    }
    @Bean
    public RestTemplate journeyRestTemplate(HttpLogInterceptor httpLogInterceptor) throws Exception {
        final RestTemplate restTemplate = buildRestTemplate();
        restTemplate.setErrorHandle(new NoOpResponseErrorHandler());
        restTemplate.setInterceptors(Collections.singletonList(httpLogInterceptor));
        return restTemplate;
    }

@Bean
public RestTemplate mockRestTemplate(HttpLogInterceptor httpLogInterceptor) throws Exception {
    final RestTemplate restTemplate = buildRestTemplate();
    restTemplate.setErrorHandle(new NoOpResponseErrorHandler());
    restTemplate.setInterceptors(Collections.singletonList(httpLogInterceptor));
    return restTemplate;
}
@Bean
public HttpLogInterceptor httpLogInterceptor(LogService logService) {
    return new HttpLogInterceptor(logService); }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper(); }


/* Builds a rest remplate that trusts all SSL certifications. This is necessary for calling our journeys.
 */

private RestTemplate buildRestTemplate() throws Exception {
TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, (hostname, session) CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
requestFactory.setHttpClient(httpClient);
return new RestTemplate(requestFactory);
}
}