package hackday.camunda.delegate;

import hackday.camunda.webmerge.WebmergeConnector;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateDocumentDelegate implements JavaDelegate {

    @Autowired
    WebmergeConnector connector;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String documentReference = (String) delegateExecution.getVariable("documentReference");
        connector.getDocument(documentReference);
    }
}
