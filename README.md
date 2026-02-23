# BDD Test Core
Provides core functionality and step definitions for writing tests using the Cucumber framework.

<!-- TOC -->
* [BDD Test Core](#bdd-test-core)
  * [Usage](#usage)
    * [How It Works](#how-it-works)
      * [ScenarioContext](#scenariocontext)
      * [Backend Mock "TRequest"](#backend-mock--trequest-)
    * [Properties](#properties)
<!-- TOC -->

## Usage
Import into your Cucumber test project.

    <dependency>
        <groupId>uk.gov.hmrc.eis.tests</groupId>
        <artifactId>bdd-test-core</artifactId>
        <version>{bdd-test-core.version}</version>
    </dependency>

Then, ensure your `CucumberTestRunner` has the package `uk.gov.hmrc.eis.tests.cucumber` as one of it's Glue properties. E.g.:

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,
    value = "uk.gov.gsi.hmrc.test, uk.gov.hmrc.eis.tests.cucumber")
public class CucumberTestRunner { ... }
```
Finally, ensure your `CucumberTestContext` has the package `uk.gov.hmrc.eis.tests.cucumber` included in component scan, to ensure all beans are created. E.g.:

```java
@CucumberContextConfiguration
@SpringBootTest
public class CucumberTestContext {
    @Configuration
    @ComponentScan(basePackages = {
        "uk.gov.gsi.hmrc.test",
        "uk.gov.hmrc.eis.tests.cucumber"
    })
    @EnableAutoConfiguration
    public static class TestConfig { ... }
}

```

### How It Works

The library provides, out of the box, a number of generic step definitions. It also provides a number of useful services, which will be created as beans within your
spring context when running your tests. Some of the useful beans you will have access to are highlighted below.

#### ScenarioContext

The scenario context does what it says on the tin! It holds contextual data for the current scenario being executed. It achieves this by using
annotation `@ScenarioScope`, which means the bean will be re-created for each scenario being executed. This allows you to run scenarios completely
independently of one another, and allows them to be thread-safe during a parallel run. It contains some useful generic information, such as the headers
you wish to send with your request (or indeed, in your message for MQ based journeys), or the payload you wish to send.

#### Backend Mock "TRequest"

A key part of a lot of existing tests in EIS that use a SoapUI WAR as a backend mock is something called a "TRequest" (short for "Transformed Request"). This is a unique test scenario header
that can be sent **directly** to the mock, and not via the service. The request will include a correlation ID and the mock will respond with the exact details of 
the request it received for that correlation ID. This is useful to assert that any transformations have taken place, the correct headers were sent to the backend, and a number of
other things. 

Traditionally, in most backend mocks, the response for the "TREQUEST" scenario consists of a body, which contains the content that was received. All of the response headers will be the request
headers that were received. However, there is another option, whereby the mock can return this same data, but as a JSON object in the body of the response. If your mock does choose to take this approach,
you can easily override the behaviour to ensure your tests adhere to this by just defining a bean as below in your test project:

```java
@Configuration
public class BeanConfig {
  @Bean
  @Primary
  public BackendMockService jsonBackendMockService(
      RestTemplate mockRestTemplate,
      MockProperties mockProperties,
      ScenarioContext scenarioContext
  ) {
    return new JsonBackendMockService(
        mockRestTemplate,
        mockProperties,
        scenarioContext
    );
  }
}
```

### Properties

The following are all properties that can be optionally defined.

| Property                            | Description                                                                                                                                          | Default   |
|-------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|
| journey.url                         | The url of your journey. In Dev/CI profiles you should look to set this using environment variables injected by the pipeline.                        |           |
| journey.path                        | The context path of your API.                                                                                                                        |           |
| journey.additionalPath              | Any additional path your journey may have, including any path variables.                                                                             |           |
| journey.payloadType                 | The payload type of your API.                                                                                                                        | XML       |
| mock.url                            | The URL of your mock. In Dev/CI profiles you should look to set this using environment variables injected by the pipeline.                           |           |
| mock.path                           | The context path of your mock.                                                                                                                       |           |
| mock.method                         | The HTTP method your mock endpoint uses.                                                                                                             | POST      |
| mock.validSoapPayload               | The filename of a valid soap payload. Only required for SOAP backend mocks written in in SoapUI, where the TREQUEST is being used for assertions.    |           |
| mock.soap                           | Whether or not the backend mock is using SOAP.                                                                                                       | False     |
| mock.soapAction                     | The soapAction used by the backend mock. Only required for SOAP backend mocks written in in SoapUI, where the TREQUEST is being used for assertions. |           |
| mock.tRequestMaxWaitMillis          | The max wait time in milliseconds when querying for successful TRequest from the mock.                                                               | 5000      |
| mock.tRequestPollIntervalMillis     | The poll interval in milliseconds when querying for successful TRequest from the mock.                                                               | 500       |
| elastic.hostname                    | The elasticsearch hostname to use for querying trace points.                                                                                         | localhost |
| elastic.port                        | The elasticsearch port to use for querying trace points.                                                                                             | 8080      |
| test.traceEventsIndex               | The trace points index to use for querying trace points.                                                                                             |           |
| test.tracePointQueryMaxWaitSeconds  | The max wait time in seconds when querying for trace points.                                                                                         | 15        |
| test.tracePointQueryIntervalSeconds | The interval in seconds between each trace point query.                                                                                              | 1         |
| tracepoints.assertionsEnabled       | Whether or not trace point assertions are enabled. Useful for running tests against local journeys where trace point assertions would always fail.   | True      |

OFFICIAL

This message and any attachments are confidential. Unless you are the intended recipient, the information must not be used, disclosed, or copied to any other person who is not entitled to receive it. If you have received this message in error, please notify the sender immediately and then delete it. The Commissioners for HM Revenue and Customs are not liable for any personal views of the sender.

To help HMRC fight cybercrime, forward suspicious emails to phishing@hmrc.gov.uk and texts to 60599. Find advice and report tax scam phone calls on GOV.UK.
