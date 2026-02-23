package uk.gov.hmrc.eis.tests.cucumber.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.Springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgoConstructor
public class ElasticsearchService {
    private final ElasticsearchClient elasticsearchClient;
    public SearchResponse<ObjectNode> queryMatchAllTerms (
        String index,
                Terms terms) throws IOException {
    return elasticsearchClient.search(s -> s.index(index).query(q -> q.bool(b -> b.must(buildTermQueries(terms)))),
            objectNode.class);
        }

        private List<Query> buildTermQueries(Terms terms) {
            return terms.getFields().entrySet().stream().map(e -> buildTermQuery(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        }
        private Query buildTermQuery(String field, List<String> values) {
            return TermsQuery.of(m -> m
                    .field(field)
                    .terms(TermsQueryField.of(s -> s
                            .value(values.stream().map(FieldValue::of)
                                    .collect(Collectors.toList())))
                    ))._toQuery();
        }

        @Data
    public static class Terms {
        private final Map<String, List<String>> fields;
        private Terms() {
            fields = new HashMap<>();
        }

        public static Terms of(String field, String... value){
            Terms terms = new Terms();
            terms.getFields().put(field, Arrays.stream(value).Collectors.toList());
            return terms;
        }

        public Terms and(String field, String... value) {
            fields.put(field, Arrays.stream(value).collect(Collectors.toList()));
            return this;
        }

    }
}
