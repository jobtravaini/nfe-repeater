package com.job.nfe_repeater.arquivei.rest;

import com.job.nfe_repeater.arquivei.domain.response.NfeReceivedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component()
public class NfeReceivedConsumer {

    @Value("${arquivei.nfe.received.x-api-id}")
    private String apiId;
    @Value("${arquivei.nfe.received.x-api-key}")
    private String apiKey;
    @Value("${arquivei.nfe.received.url}")
    private String baseUrl;

    public NfeReceivedResponse getNfe() {
        return getNfe(baseUrl);
    }

    public NfeReceivedResponse getNfe(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = generateHeader();
        ResponseEntity<NfeReceivedResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, NfeReceivedResponse.class);

        return response.getBody();
    }

    private HttpEntity<String> generateHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("x-api-id", apiId);
        headers.add("x-api-key", apiKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>("body", headers);
    }
}
