package hackday.camunda.webmerge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebmergeConnector {

    private static final String MERGE = "merge";

    private static final String DOWNLOAD = "download=1";

    @Value("${application.document.webmerge.api.url}")
    private String apiUrl;

    private RestTemplate webmergeRestTemplate;

    public WebmergeConnector(@Value("${application.document.webmerge.api.key}") String apiKey,
                             @Value("${application.document.webmerge.api.secret}") String apiSecret) {
        this.webmergeRestTemplate = new RestTemplate();
        webmergeRestTemplate.setErrorHandler(new WebmergeErrorHandler());
        webmergeRestTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(apiKey, apiSecret));
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2 * 60000);
        requestFactory.setReadTimeout(2 * 60000);
        webmergeRestTemplate.setRequestFactory(requestFactory);
    }

    /*
     * When getting a static document from Webmerge we have to pass an Object with at least one key:value pair
     * otherwise Webmerge will throw Exception
     */
    public byte[] getDocument(String documentReference) throws Exception {
        URI uri = getUri(documentReference);
        Map<String, String> dummyMap = new HashMap<>();
        dummyMap.put("none", "none");
        byte[] content;
        try {
            content = webmergeRestTemplate.postForEntity(uri, dummyMap, byte[].class).getBody();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return content;
    }

    public byte[] mergeDocument(String documentReference, Object replacements) throws Exception {
        URI uri = getUri(documentReference);
        byte[] content;
        try {
            content = webmergeRestTemplate.postForEntity(uri, replacements, byte[].class).getBody();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return content;
    }

    private URI getUri(String documentReference) {
        return UriComponentsBuilder.fromHttpUrl(apiUrl)
                .pathSegment(MERGE)
                .pathSegment(documentReference)
                .query(DOWNLOAD).build().toUri();
    }
}
