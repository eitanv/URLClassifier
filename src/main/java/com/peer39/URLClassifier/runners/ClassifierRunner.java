package com.peer39.URLClassifier.runners;

import com.peer39.URLClassifier.model.Keyword;
import com.peer39.URLClassifier.model.KeywordCategory;
import com.peer39.URLClassifier.rest.ClassifierController;
import com.peer39.URLClassifier.services.CategoryClassifierService;
import com.peer39.URLClassifier.services.ModelInitializerService;
import com.peer39.URLClassifier.services.URLService;
import com.peer39.URLClassifier.services.WebContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ClassifierRunner implements CommandLineRunner {

    @Autowired
    private URLService urlService;
    @Autowired
    private WebContentService webContentService;
    @Autowired
    private ClassifierController classifierController;
    @Autowired
    private ModelInitializerService initializerService;
    @Autowired
    private CategoryClassifierService CategoryClassifierService;

    @Override
    public void run(String... args) {
        //initializeModel();
        List<KeywordCategory> inputCategories = getCategoriesFromArgs(args[0]);
        List<String> urls = getUrlsFromArgs(args[1]);

        Map<String, String> urlToCleanedContentMap = classifierController.getUrlsTexts(urls);

        // For each URL in the map, classify the content
        urlToCleanedContentMap.forEach((url, cleanedContent) -> {
            List<String> matchedCategories = CategoryClassifierService.classifyURL(cleanedContent, inputCategories);
            matchedCategories.forEach(category -> {
                System.out.println("Found a match to category: " + category);
            });
        });
    }

    private List<KeywordCategory> getCategoriesFromArgs(String arg) {
        String[] inputCategories = arg.split(";");
        List<KeywordCategory> categories = new ArrayList<>();
        for (String categoryName : inputCategories) {
            for (KeywordCategory category : initializerService.getCategories()) {
                if (category.getCategoryName().equals(categoryName)) {
                    categories.add(category);
                    break;
                }
            }
        }
        return categories;

    }

    private List<String> getUrlsFromArgs(String arg) {
        List<String> inputURLs = Arrays.asList(arg.split(";"));
        return inputURLs;
    }

    private String processUrl(String url) {
        try {
            String originalContent = urlService.getTextFromUrl(url);
            return webContentService.getTextFromUrl(originalContent);
        } catch (Exception e) {
            System.out.println("Error processing URL: " + url + ": " + e.getMessage());
            return "";
        }
    }
    //Unused method to initialize the categories if not injected from the ModelInitializerService
    private static List<KeywordCategory> allCategories = null;

    private void initializeModel() {
        if (allCategories == null) {
            allCategories = new ArrayList<>();

            KeywordCategory starWarsCategory = new KeywordCategory();
            starWarsCategory.setCategoryName("Star Wars");
            starWarsCategory.setKeywords(Arrays.asList(new Keyword("Star Wars"), new Keyword("star war"), new Keyword("starwars"), new Keyword("starwar"), new Keyword("r2d2"), new Keyword("may the force be with you")));
            allCategories.add(starWarsCategory);

            KeywordCategory basketballCategory = new KeywordCategory();
            basketballCategory.setCategoryName("Basketball");
            basketballCategory.setKeywords(Arrays.asList(new Keyword("basketball"), new Keyword("nba"), new Keyword("ncaa"), new Keyword("lebron james"), new Keyword("john stokton"), new Keyword("anthony davis")));
            allCategories.add(basketballCategory);
        }
    }
}