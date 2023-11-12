package com.tracker.config.tools;

import com.tracker.config.parser.AvlData;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.tracker.config.tools.constants.*;

public class RestConfig {



    public static String getToken() {
        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("email", USERNAME);
        requestBody.add("password", PASSWORD);
        HttpEntity entity = new HttpEntity<MultiValueMap>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL+LOGIN_REST, HttpMethod.POST, entity, String.class);
        return "Bearer "+response.getHeaders().get(ACCESS_TOKEN).get(0);
    }


    public static String sendListAvlData(List<AvlData> lstAvlData){
        RestTemplate restTemplate = new RestTemplate();
        String token = getToken();
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Authorization", token);

        HttpEntity<List<AvlData>> entity = new HttpEntity<>(lstAvlData,headers);
        String url = BASE_URL+USER_URL+USER_URL_CREATE_AVLDATA;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println("before done "+response.getBody());
        return response.getBody();
    }
}