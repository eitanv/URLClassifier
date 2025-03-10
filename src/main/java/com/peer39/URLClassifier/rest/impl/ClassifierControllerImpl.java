package com.peer39.URLClassifier.rest.impl;

import com.peer39.URLClassifier.rest.ClassifierController;
import com.peer39.URLClassifier.services.CategoryClassifierService;
import com.peer39.URLClassifier.services.ModelInitializerService;
import com.peer39.URLClassifier.services.URLService;
import com.peer39.URLClassifier.services.WebContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ClassifierControllerImpl implements ClassifierController {

    @Autowired
    private URLService urlService;
    @Autowired
    private WebContentService webContentService;
    @Autowired
    private ModelInitializerService initializerService;
    @Autowired
    private CategoryClassifierService categoryClassifierService;

    @Override
    public Map<String, String> getUrlsTexts(List<String> urls) {
        Map<String, String> urlToCleanedContentMap = new ConcurrentHashMap<>();
        // Process each URL asynchronously
        CompletableFuture[] urlProcessingTasks = urls.stream().map(url -> CompletableFuture.runAsync(() -> {
            if (url == null || url.trim().isEmpty()) {
                return; // Skip null or empty URLs
            }
            String cleanedContent = processUrl(url);
            // Store the cleaned content in the map
            urlToCleanedContentMap.put(url, cleanedContent);
        })).toArray(CompletableFuture[]::new); //Store urlProcessingTasks in an array to be used by .allOf
        CompletableFuture.allOf(urlProcessingTasks).join(); // Wait for all tasks to complete
        return urlToCleanedContentMap;
    }

    private String processUrl(@NonNull String url) {
        try {
            String originalContent = urlService.getTextFromUrl(url); // Fetch the raw HTML content
            return webContentService.getTextFromUrl(originalContent); // Clean the content from tags and scripts
        } catch (Exception e) {
            //System.out.println("Error processing URL: " + url + ": " + e.getMessage());
            return "";
        }
    }

    @Override
    public boolean isURLinCategory(List<String> urls) {
        Map<String, String> urlToCleanedContentMap = getUrlsTexts(urls); //Get the cleaned content from the URLs
        return categoryClassifierService.isURLinCategory(urlToCleanedContentMap.values(), initializerService.getCategories()); //Check if any URL content matches any category phrases
    }
}
