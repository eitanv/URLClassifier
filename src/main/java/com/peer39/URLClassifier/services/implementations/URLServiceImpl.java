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

    /**
     * @param url The website's address
     * @return The raw HTML content of the page
     */
    @Override
    public String getTextFromUrl(String url) {
        String content = "";
        try {
            content = restTemplate.getForObject(url, String.class); //Calling the URL to retrieve the HTML content
        } catch (Exception e) {
            System.out.println("Error fetching content from " + url.substring(0, 30) + ": " + e.getMessage().substring(0, 40));
        }
        return content == null ? "" : content; //getForObject can return null, empty string is returned in that case
    }

}
