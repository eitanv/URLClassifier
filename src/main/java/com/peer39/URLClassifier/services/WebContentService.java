package com.peer39.URLClassifier.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class WebContentService {

    public WebContentService() {
    }

    public String getTextFromUrl(String htmlContent) {
        if (htmlContent == null) {
            return "";
        }
        try {
            Document document = Jsoup.parse(htmlContent);
            document.select("script, style").remove();
            return document.text();
        } catch (Exception e) {
            System.out.println("Error parsing HTML content: " + e.getMessage());
            return "";
        }
    }
}


