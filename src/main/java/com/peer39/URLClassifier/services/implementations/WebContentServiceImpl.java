package com.peer39.URLClassifier.services.implementations;

import com.peer39.URLClassifier.services.WebContentService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class WebContentServiceImpl implements WebContentService {

    public WebContentServiceImpl() {
    }

    @Override
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