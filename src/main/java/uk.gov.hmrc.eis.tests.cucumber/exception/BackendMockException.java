package uk.gov.hmrc.eis.tests.cucumber.exception;

public class BackendMockException extends RuntimeException {
    public BackendMockException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackendMockException(String message){
        super(message);
    }
}