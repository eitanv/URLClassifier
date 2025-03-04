package com.peer39.URLClassifier.services.implementations;

import com.peer39.URLClassifier.services.WebContentService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class WebContentServiceImpl implements WebContentService {

    public WebContentServiceImpl() {
    }

    /**
     * @param htmlContent The raw HTML content of a web page
     * @return The cleaned text of a web page, excluding script and style elements
     */
    @Override
    public String getTextFromUrl(String htmlContent) {
        if (htmlContent == null) {
            return ""; // Handle null input with empty String
        }
        try {
            Document document = Jsoup.parse(htmlContent);
            document.select("script, style").remove(); // Removes script and tags
            return document.text();
        } catch (Exception e) {
            System.out.println("Error parsing HTML content: " + e.getMessage());
            return "";
        }
    }
}