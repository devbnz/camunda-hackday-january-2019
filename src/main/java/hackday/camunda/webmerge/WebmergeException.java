package hackday.camunda.webmerge;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Map;

public class WebmergeException extends HttpClientErrorException {

    private String error;

    private HttpStatus httpStatus;

    public WebmergeException(ClientHttpResponse response) throws IOException {
        super(response.getStatusCode());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = mapper.readValue(response.getBody(), Map.class);
        error = jsonMap.get("error");
        httpStatus = response.getStatusCode();
    }

    private String getError() { return error; }

    private HttpStatus getHttpStatusCode() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return "Webmerge error " + getHttpStatusCode() + ", " + getError() ;
    }
}
