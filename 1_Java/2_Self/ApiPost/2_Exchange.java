public CustomerResponseDTO exchange(String apiUrl, HttpMethod method, Object reqBody) {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity<Object> requestEntity = new HttpEntity<>(reqBody, headers);
	RestTemplate restTemplate = getRestTemplate();
	if (method.equals(HttpMethod.GET)) {
		return restTemplate.getForEntity(apiUrl, CustomerResponseDTO.class).getBody();
	}
	return restTemplate.exchange(apiUrl, method, requestEntity, CustomerResponseDTO.class).getBody();
}

public RestTemplate getRestTemplate() {
	SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
	int timeout = 2 * 60 * 1000;
	requestFactory.setConnectTimeout(timeout);
	requestFactory.setReadTimeout(timeout);
	return new RestTemplate(requestFactory);
}

HttpMethod.POST