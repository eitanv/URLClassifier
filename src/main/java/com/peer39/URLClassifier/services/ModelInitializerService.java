package com.peer39.URLClassifier.services;

import com.peer39.URLClassifier.model.Keyword;
import com.peer39.URLClassifier.model.KeywordCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ModelInitializerService {

    private List<KeywordCategory> categories;

    public ModelInitializerService() {
        initializeModel();
    }

    public List<KeywordCategory> getCategories() {
        return categories;
    }

    private void initializeModel() {
        categories = new ArrayList<>();

        KeywordCategory starWarsCategory = new KeywordCategory();
        starWarsCategory.setCategoryName("Star Wars");
        starWarsCategory.setKeywords(Arrays.asList(new Keyword("Star War"), new Keyword("star war"), new Keyword("starwars"), new Keyword("starwar"), new Keyword("r2d2"), new Keyword("may the force be with you")));
        categories.add(starWarsCategory);

        KeywordCategory basketballCategory = new KeywordCategory();
        basketballCategory.setCategoryName("Basketball");
        basketballCategory.setKeywords(Arrays.asList(new Keyword("basketball"), new Keyword("nba"), new Keyword("ncaa"), new Keyword("lebron james"), new Keyword("john stokton"), new Keyword("anthony davis")));
        categories.add(basketballCategory);
    }
}