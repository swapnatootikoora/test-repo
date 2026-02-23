package uk.gov.hmrc.eis.tests.cucumber.service;

import io.cucumber.datatable.DataTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gov.hmrc.eis.tests.cucumber.CucumberConstants;
import uk.gov.hmrc.eis.tests.cucumber.ScenarioContext;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataSubstitutionService {
    private final ScenarioContext scenarioContext;

    /* Takes a datatable with two columns and converts it into a map of pairs, substituting any key values.
    Substituted values;  -$uuid into {@Link ScenarioContext}'s Correlation id
    @param dataTable a Two column cucumber DataTable
    @return Mapping of String to String
     */

    public Map<String, String> processDataTable(DataTable dataTable) {
        return dataTable.asMap()
        .entrySet()
        .stream()
                .map(e -> new SimpleEntry<>(e.getKey(), substituteVariable(e.getValue())))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
    public String substititeVariables(String str, Map<String, String> variables){
        for(Entry<String, String> e : variables.entrySet()) {
            if(e.getValue().equals(CucumberConstants.REMOVE_ATTRIBUTE)){
                str = str.replaceAll("\\$" +e.getKey(), "");
            }
            else {
                str = str.replaceAll("\\$" +e.getKey(),e.getValue());
            }
        }
        return str;
    }
    private String substituteVariable(String value){
        if(value.equals(CucumberConstants.UUID_SUBSTITUTE)){
            return scenarioContext.getCorrleationId();
        }
        return value;
    }
}