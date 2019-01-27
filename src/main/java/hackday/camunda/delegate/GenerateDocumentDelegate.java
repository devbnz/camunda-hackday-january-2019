package hackday.camunda.delegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hackday.camunda.webmerge.WebmergeConnector;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class GenerateDocumentDelegate implements JavaDelegate {

    @Autowired
    WebmergeConnector connector;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String documentReference = "218140/dlcqtv";
        String headlineTitle = (String) delegateExecution.getVariable("news");
        String json = (String) delegateExecution.getVariable("weather");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeRoot = objectMapper.readTree(json);
        JsonNode jsonNodeWheather = jsonNodeRoot.get("weather");
        String wetter = jsonNodeWheather.get("main").asText();
        Map<String, Object> replacements = new HashMap<>();
        replacements.put("headline", headlineTitle);
        replacements.put("date", LocalDateTime.now());
        replacements.put("temperatur", wetter);
        connector.mergeDocument(documentReference, replacements);

        ObjectMapper
    }
}
