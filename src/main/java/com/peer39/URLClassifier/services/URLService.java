package com.peer39.URLClassifier.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class URLService {

    private final RestTemplate restTemplate;

    public URLService() {
        this.restTemplate = new RestTemplate();
    }

    public String getTextFromUrl(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}

