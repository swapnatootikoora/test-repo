package uk.gov.hmrc.eis.tests.cucumber.service.backendmock;
 import org.springframework.http.HttpMethod;
 import org.springframework.http.ResponseEntity;
 import org.springframework.util.MultiValueMap;

 public interface BackendMockService {
 <T> ResponseEntity<T> executeRequest(
         HttpMethod method,
         MultiValueMap<String, String> headers,
         Class<T> type
 );
 <T> ResponseEntity<T> executeRequest(
         HttpMethod method,
         MultiValueMap<String, String> headers,
         String payload,
         Class<T> type
 );
 void executeTRequest();
}

