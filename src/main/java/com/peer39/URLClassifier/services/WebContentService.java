package com.peer39.URLClassifier.services;

public interface WebContentService {

    /**
     * @param htmlContent The raw HTML content of a web page
     * @return The cleaned text of a web page, excluding script and style elements
     */
    String getTextFromUrl(String htmlContent);

}


