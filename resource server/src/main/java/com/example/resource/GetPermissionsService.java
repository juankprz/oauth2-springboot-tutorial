package com.example.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GetPermissionsService {

	@Autowired
	private Environment env;
	@Autowired
	private RestTemplate restTemplate;
	ResponseEntity<String> entity;

	public String Validate(String Token,String name) {
		String url="http://localhost:8130/arquetipo/v1/saludo/test";
		 ResponseEntity<String> response=null;
		try {
			 HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);  // Set content type (optional)
		        headers.set("Authorization", Token);  // Set authorization header (optional)

		        // Create map for parameters
		        Map<String, String> params = new HashMap<>();
		        params.put("name",name);

		        // Create HttpEntity with headers and (optional) body
		        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		        // Perform the GET request with exchange method
                response = restTemplate.exchange(url+"?name="+name, HttpMethod.GET, requestEntity, String.class, params);

			
	}catch(Exception E) {
		
	}
		System.out.println(response.getBody());
		return response.getBody();
		
	}
}
