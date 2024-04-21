package com.eland.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ApiUtil {

    private ApiUtil() {}

    public static <T, E> E postForEntity(T request, Class<E> clazz, String apiUrl, int connectTimeout, int readTimeout) {
        RestTemplate restTemplate = getRestTemplate(connectTimeout, readTimeout);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> requestBody = new HttpEntity<>(request, headers);
        ResponseEntity<E> result = restTemplate.postForEntity(apiUrl, requestBody, clazz);
        return result.getBody();
    }

    public static RestTemplate getRestTemplate(int connectTimeout, int readTimeout) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return new RestTemplate(requestFactory);
    }

}
