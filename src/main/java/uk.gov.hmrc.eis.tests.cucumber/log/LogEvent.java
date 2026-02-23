package uk.gov.hmrc.eis.tests.cucumber.log;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
public abstract class LogEvent {
    public abstract String getLogType();
    public abstract String toString();
}