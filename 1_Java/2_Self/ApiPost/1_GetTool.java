package com.tools.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ApiTool {

    // url=...?name=Tom&age=18
    public String getOmsApiRep(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }

    public static <E> E getForEntity(Class<E> clazz, String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<E> response = restTemplate.getForEntity(url, clazz);
        return response.getBody();
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJhbGciOiJ");
        return headers;
    }

}
