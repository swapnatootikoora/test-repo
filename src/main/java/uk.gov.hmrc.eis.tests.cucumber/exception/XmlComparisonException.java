package uk.gov.hmrc.eis.tests.cucumber.exception;

public class XmlComparisonException extends RuntimeException {
    public XmlComparisonException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlComparisonException(String message){
        super(message);
    }
}