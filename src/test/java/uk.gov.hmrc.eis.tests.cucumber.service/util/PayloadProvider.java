package
uk.,gov.hmrc.eis.tests.cucumber.service.util;

/* A functional interface to encapsulate provision of test resources. @/


 */

@FunctionalInterface
public interface PayloadProvider {
    /*  provides the string value of the resource named.
    @param resourcename the name of the resource being requested
    @return the vale of the requested resources as a string.
     */

    String provide(final String resourceName);
}