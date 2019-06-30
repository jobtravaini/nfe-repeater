package com.job.nfe_repeater.arquivei.rest;

import com.job.nfe_repeater.arquivei.domain.response.NfeData;
import com.job.nfe_repeater.arquivei.domain.response.NfeReceivedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Component
public class NfeReceivedConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NfeReceivedConsumer.class);
    private static final String API_KEY_CODE = "x-api-key";
    private static final String API_ID_CODE = "x-api-id";

    @Value("${arquivei.nfe.received.x-api-id}")
    private String apiId;
    @Value("${arquivei.nfe.received.x-api-key}")
    private String apiKey;
    @Value("${arquivei.nfe.received.url}")
    private String baseUrl;
    @Value("${arquivei.nfe.received.max-result}")
    private int maxResultCount;

    public List<NfeData> getNfeList() {
        return getNfeList(baseUrl);
    }

    private List<NfeData> getNfeList(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = generateHeader();
        LOGGER.info(MessageFormat.format("Consuming endpoint {0} to retrieve nfe list", url));
        ResponseEntity<NfeReceivedResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, NfeReceivedResponse.class);
        LOGGER.info("Response HTTP Status: " + response.getStatusCode().value());
        validateResponse(response);
        List<NfeData> result = response.getBody().getData();
        if (isNextPageRequired(response.getBody())) {
            result.addAll(getNfeList(response.getBody().getPageInformation().getNext()));
        }

        return result;
    }

    private void validateResponse(ResponseEntity<NfeReceivedResponse> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            String errorMessage = MessageFormat.format("An error occured while connecting to endpoint {0} during database migration phase. Reason phrase: {1}", baseUrl, response.getStatusCode().getReasonPhrase());
            throw new RuntimeException(errorMessage);
        }

        if (response.getBody() == null) {
            throw new RuntimeException("Unexpected response. The response body is null.");
        }
    }

    private boolean isNextPageRequired(NfeReceivedResponse response) {
        return response.getData().size() >= maxResultCount && null != response.getStatus() && !StringUtils.isEmpty(response.getPageInformation().getNext());
    }

    private HttpEntity<String> generateHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(API_ID_CODE, apiId);
        headers.add(API_KEY_CODE, apiKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>("parameters", headers);
    }
}
