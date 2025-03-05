package com.peer39.URLClassifier.rest;

import com.peer39.URLClassifier.services.URLService;
import com.peer39.URLClassifier.services.WebContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classifier")
public class ClassifierController {

    @Autowired
    private URLService urlService;
    @Autowired
    private WebContentService webContentService;

    /**
     * @param urls A list of URLs to process
     * @return A map of URLs to their cleaned text content
     */
    @PostMapping("/urls")
    public Map<String, String> getUrlsTexts(@RequestBody List<String> urls) {
        Map<String, String> urlToCleanedContentMap = new HashMap<>();
        // Process each URL asynchronously
        CompletableFuture<Void>[] urlProcessingTasks = urls.stream().map(url -> CompletableFuture.runAsync(() -> {
            String cleanedContent = processUrl(url);
            // Store the cleaned content in the map
            synchronized (urlToCleanedContentMap) {
                urlToCleanedContentMap.put(url, cleanedContent);
            }
        })).toArray(CompletableFuture[]::new); //Store urlProcessingTasks in an array to be used by .allOf
        CompletableFuture.allOf(urlProcessingTasks).join(); // Wait for all tasks to complete
        return urlToCleanedContentMap;
    }

    private String processUrl(@NonNull String url) {
        String originalContent = urlService.getTextFromUrl(url); // Fetch the raw HTML content
        String cleanedContent = webContentService.getTextFromUrl(originalContent); // Clean the content from tags and scripts
        return cleanedContent;
    }
}