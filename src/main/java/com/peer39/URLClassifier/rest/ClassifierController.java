package com.peer39.URLClassifier.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classifier")
public interface ClassifierController {

    /**
     * @param urls A list of URLs to process
     * @return A map of URLs to their cleaned text content
     */
    @PostMapping("/urls")
    Map<String, String> getUrlsTexts(@RequestBody List<String> urls);
}