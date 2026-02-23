package uk.gov.hmrc.eis.tests.cucumber.config;

import io.cucumber.java8.En;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;

public class HookConfig implements En {
    public HookConfig(ScenarioContext testContext){
        Before(testContext::setScenario);
    }
}