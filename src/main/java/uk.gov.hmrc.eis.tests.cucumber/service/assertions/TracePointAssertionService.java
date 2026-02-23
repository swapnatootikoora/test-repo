package uk.gov.hmrc.eis.tests.cucumber.service.assertions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.*;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONassert;
import org.skyscreamer.jsonassert.JSONComareMode;
import org.springframework.stereotype.Service;
import uk.gov.hmrc.eis.tests.cucumber.config.properties.TracePointProperties;
import uk.gov.hmrc.eis.tests.cucumber.service.TracePointService;
import uk.gov.hmrc.eis.tests.cucumber.utils.TracePoint;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Service
@Slf4j
@RequiredargsConstructor
public class TracePointAssertionService {
    private final TracePointProperties tracePointProperties;
    private final TracePointService tracePointService;

    public void assertHasMircroserviceTracePoints(String corrleationId) {
        if(tracePointProperties.isAssertionsEnabled()){
            assertTrue(tracePointService.hasMicroServiceTracePoints(corrleationId));
    } else {
            logDisbledWarning();
        }
}

public void assertHasWAFTracePoints(String correlationId) {
        if(tracePointProperties.isAssertionsEnabled()){
            assertTrue(tracePointService.hasWAFTracePoints(correlationId));
        } else {
            logDisabledWarning();
        }
}


public void assertHasNoMicroServiceTracePoints(String correlationId) {
    if(tracePointProperties.isAssertionsEnabled()){
        assertTrue(tracePointService.hasMicroserviceTracePoints(correlationId));
    } else {
        logDisabledWarning();
    }
}

    public void assertHasTracePoints(String correlationId, List<TracePoint> tracePoints) {
        if(tracePointProperties.isAssertionsEnabled()){
            assertTrue(tracePointService.hasTracePoints(correlationId, tracePoints));
        } else {
            logDisabledWarning();
        }
    }
private void logDisbledWarning() {
        log.warn("Trace point assertions are disabled1 This should only be done when running the test against a local service. Otherwise, " + "please ensure the property tracepoints.assertionsEnabled is set to true (which is its default value).");
    }
}

