package uk.gov.hmrc.eis.tests.cucumber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import uk.gov.hmrc.eis.tests.cucumber.config.properties.ElasticSearchProperties;

import java.beans.BeanProperty;

@Configuration
public class ElasticsearchClientConfig {
    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticSearchProperties elasticSearchProperties) {
        RestClient restClient = RestClient.builder(
                new HttpHost(elasticSearchProperties.getHostname(), elasticSearchProperties.getPort())
        ).setDefaultHeaders(new Header[]{
                new BasicHeader("Content-type", "application/json")
        })
                .setHttpClientConfigCallback(hc -> hc
                        .addInterceptorLast((HttpResponseInterceptor) (response, context) ->
                                response.addHeader("X-Elastic-Product", "Elasticsearch")))
                .build();

        return new ElasticsearchClient(
                new RestClientTransport(restClient, new JacksonJsonMapper())
        );
    }
}


