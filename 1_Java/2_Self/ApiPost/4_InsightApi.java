private InsightResponse postInsightApi(InsightRequest postBody) {
	MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
	parameters.add("action", "search");
	parameters.add("txtInput_json", gson.toJson(postBody));
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);
	try {
		String response = new RestTemplate().postForObject(insightPropertiesBean.getTdsUrl(), entity, String.class);
		return gson.fromJson(response, InsightResponse.class);
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

public InsightResponse postInsightApi(InsightRequest postBody) {
	RestTemplate restTemplate = new RestTemplate();
	MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
	postParameters.add("action", "search");
	postParameters.add("txtInput_json", gson.toJson(postBody));
	HttpHeaders headers = new HttpHeaders();
	headers.add("Content-Type", "application/x-www-form-urlencoded");
	HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(postParameters, headers);
	return restTemplate.postForObject(insightPropertiesBean.getTdsUrl(), entity, InsightResponse.class);
}