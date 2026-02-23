package uk.gov.hmrc.eis.tests.cucumber.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmrc.eis.tests.cucumber.log.LogEvent;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {
    private final ObjectMapper;
    public void log(LogEvent logEvent) throws JsonProcessingException {
        log.info(objectMapper.writeWithDefaultPrettyPrinter().writeValueAsString(logEvent));
    }
}