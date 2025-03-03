package com.peer39.URLClassifier.rest;

import com.peer39.URLClassifier.services.URLService;
import com.peer39.URLClassifier.services.WebContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classifier")
public class ClassifierController {

    @Autowired
    private URLService urlService;

    @Autowired
    private WebContentService webContentService;

    private static final String EXAMPLE_URL = "https://www.example.com";

    @PostMapping("/urls")
    public Map<String, String> getUrlsTexts(@RequestBody List<String> urls) {
        Map<String, String> urlToCleanedContentMap = new HashMap<>();
        if (urls.isEmpty()) {
            urls.add(EXAMPLE_URL);
        }
        for (String url : urls) {
            urlToCleanedContentMap.put(url, processUrl(url));
        }
        return urlToCleanedContentMap;
    }

    private String processUrl(String url) {
        String originalContent = urlService.getTextFromUrl(url);
        System.out.printf("Original content from %s:%n%s%n", url, originalContent);

        String cleanedContent = webContentService.getTextFromUrl(originalContent);
        System.out.printf("Cleaned text from %s:%n%s%n", url, cleanedContent);

        return cleanedContent;
    }
}