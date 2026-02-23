package uk.gov.hmrc.eis.tests.cucumber;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngine;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import javax.management.ConstructorParameters;

@Suite
@IncludeEngine("cucumber")
@SelectClasspathResource("features")
@configurationParameter(key=GLUE_PROPERTY_NAME, value = "uk.gov.gsi.hmrc.test, uk.gov.hmrc.eis.tests.cucumber,uk.gov.hmrc.dct129h")
@configurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value= "false")
public class CucumberTestRunner {

}