package com.peer39.URLClassifier.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class WebContentService {
    public WebContentService() {
    }

    public String getTextFromUrl(String htmlContent) {

        try {
            Document document = Jsoup.parse(htmlContent);
            document.select("script, style").remove();
            return document.text();
        } catch (Exception e) {
            // Log the error and handle it gracefully
            return "Error parsing HTML content.";
        }

    }
}


