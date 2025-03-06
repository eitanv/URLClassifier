package com.peer39.URLClassifier.runners;

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
        List<KeywordCategory> inputCategories = getCategoriesFromArgs(args[0]); //Get the categories to classify by from the input names
        List<String> urls = getUrlsFromArgs(args[1]); //Get the URLs to classify from the input
        Map<String, String> urlToCleanedContentMap = classifierController.getUrlsTexts(urls); //Get the cleaned content from the URLs
        // For each URL in the map, classify the content
        urlToCleanedContentMap.values().forEach((cleanedContent) -> { //For each URL's Content
            List<String> matchedCategories = CategoryClassifierService.classifyURL(cleanedContent, inputCategories); //Return the matching Categories
            matchedCategories.forEach(category -> System.out.println("Found a match to category: " + category));
        });
    }

    private List<KeywordCategory> getCategoriesFromArgs(String arg) {
        String[] inputCategories = arg.split(";");
        List<KeywordCategory> initializedCategories = initializerService.getCategories(); //Get the predefined categories
        List<KeywordCategory> classifyByCategories = new ArrayList<>();
        for (String categoryName : inputCategories) { //For each category name in the input
            initializedCategories.stream().filter(category -> category.getCategoryName().equals(categoryName)) //If the category name is in the predefined categories
                    .findFirst().ifPresent(classifyByCategories::add); //Add it to the list of categories to classify by
        }
        return classifyByCategories;
    }

    private List<String> getUrlsFromArgs(String arg) {
        return Arrays.asList(arg.split(";"));
    }

    //Unused local variable and method to initialize the categories if not using the injected ModelInitializerService.
    // initializeModel() should  be called
    /*private static List<KeywordCategory> allCategories = null;

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
    }*/
}