package com.peer39.URLClassifier.services.implementations;

import com.peer39.URLClassifier.services.URLService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class URLServiceImpl implements URLService {

    private final RestTemplate restTemplate;

    public URLServiceImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public String getTextFromUrl(String url) {
        String content = "";
        try {
            content = restTemplate.getForObject(url, String.class); //Calling the URL to retrieve the HTML content
        } catch (Exception e) {
            //System.out.println("Error fetching content from " + url + ": " + (e.getMessage().length() > 80 ? e.getMessage().substring(0, 120) : e.getMessage()));
        }
        return content == null ? "" : content; //getForObject can return null, empty string is returned in that case
    }

}
