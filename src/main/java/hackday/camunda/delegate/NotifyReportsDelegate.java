package hackday.camunda.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

public class NotifyReportsDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RuntimeService runtimeService = execution.getProcessEngineServices()
            .getRuntimeService();

        Object report = execution.getVariable("news");


        Map<String, Object> variables= new HashMap<>();
        variables.put("news", report);
        runtimeService.correlateMessage( "Msg_ReportReceived", execution.getProcessBusinessKey(), variables);
    }
}
