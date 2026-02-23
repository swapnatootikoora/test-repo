package uk.gov.hmrc.eis.tests.cucumber.config.properties;

import lombok.Getter;

@Getter
public enum PayloadType {
    JSON(".json"),
    XML(".xml");
    private final String fileExtension;
    PayloadType(String fileExtension)
    {
        this.fileExtension = fileExtension;
    }
}