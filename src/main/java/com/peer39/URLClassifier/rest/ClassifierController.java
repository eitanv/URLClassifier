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

    @PostMapping("/urls")
    public Map<String, String> getUrlsTexts(@RequestBody List<String> urls) {
        Map<String, String> urlToCleanedContentMap = new HashMap<>();
        List<CompletableFuture<Void>> futures = urls.stream().map(url -> CompletableFuture.runAsync(() -> {
            String cleanedContent = processUrl(url);
            synchronized (urlToCleanedContentMap) {
                urlToCleanedContentMap.put(url, cleanedContent);
            }
        })).collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return urlToCleanedContentMap;
    }

    private String processUrl(@NonNull String url) {
        String originalContent = urlService.getTextFromUrl(url);
        //System.out.println("Original content from " + url + " is: " + (originalContent.isEmpty() ? "" : originalContent.substring(0, 48)));

        String cleanedContent = webContentService.getTextFromUrl(originalContent);
        //System.out.println("Cleaned content from " + url + " is: " + cleanedContent);
        return cleanedContent;
    }
}