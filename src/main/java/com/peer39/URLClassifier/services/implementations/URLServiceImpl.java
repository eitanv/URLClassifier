package com.peer39.URLClassifier.services.implementations;

import com.peer39.URLClassifier.services.URLService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class URLServiceImpl implements URLService {

    private final RestTemplate restTemplate;

    public URLServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    public String getTextFromUrl(String url) {
        String content = "";
        try {
            content = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println("Error fetching content from " + url.substring(0, 30) + ": " + e.getMessage().substring(0, 40));
        }
        return content==null? "" : content;
    }


}
