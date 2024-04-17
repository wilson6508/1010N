package com.tools.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ApiTool {

    // getRestTemplate
    public static RestTemplate getRestTemplate(int connectTimeout, int readTimeout) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return new RestTemplate(requestFactory);
    }

    public static <T, E> E postForEntity(T request, Class<E> clazz, String apiUrl, int connectTimeout, int readTimeout) {
        RestTemplate restTemplate = getRestTemplate(connectTimeout, readTimeout);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> requestBody = new HttpEntity<>(request, headers);
        ResponseEntity<E> result = restTemplate.postForEntity(apiUrl, requestBody, clazz);
        return result.getBody();
    }

    public static <T, E> E postForEntity(T request, Class<E> clazz, String apiUrl, int connectTimeout, int readTimeout) {
        RestTemplate restTemplate = getRestTemplate(connectTimeout, readTimeout);
        HttpEntity<T> requestBody = new HttpEntity<>(request);
        ResponseEntity<E> result = restTemplate.postForEntity(apiUrl, requestBody, clazz);
        return result.getBody();
    }  

    public static <T, E> E postForEntity(T request, Class<E> clazz, String apiUrl, int connectTimeout, int readTimeout, HttpHeaders headers) {
        RestTemplate restTemplate = getRestTemplate(connectTimeout, readTimeout);
        HttpEntity<T> requestBody = new HttpEntity<>(request, headers);
        ResponseEntity<E> result = restTemplate.postForEntity(apiUrl, requestBody, clazz);
        return result.getBody();
    }      

    public static String postForEntity(String apiUrl, MultiValueMap<String, String> formData, int connectTimeout, int readTimeout) {
        RestTemplate restTemplate = getRestTemplate(connectTimeout, readTimeout);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        return result.getBody();
    }

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("username", "john_doe");
    formData.add("password", "pass123");

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
    headers.setAcceptLanguage(Locale.LanguageRange.parse("zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7"));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setAcceptCharset(Collections.singletonList(StandardCharsets.ISO_8859_1));

}