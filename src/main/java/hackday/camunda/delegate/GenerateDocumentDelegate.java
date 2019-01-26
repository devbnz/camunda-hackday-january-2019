package hackday.camunda.delegate;

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
        String documentReference = (String) delegateExecution.getVariable("documentReference");
        Map<String, Object> replacements = new HashMap<>();
        replacements.put("headline", "testHeadline");
        replacements.put("date", LocalDateTime.now());
        replacements.put("wetter", "wetter");
        connector.mergeDocument(documentReference, replacements);
    }
}
