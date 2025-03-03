package com.peer39.URLClassifier.rest;

import com.peer39.URLClassifier.services.URLService;
import com.peer39.URLClassifier.services.WebContentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClassifierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLService urlService;

    @MockBean
    private WebContentService webContentService;

    /**
     * Test description:
     * This test verifies that when the list of URLs is provided, the controller correctly processes each URL
     * using the URLService and WebContentService mocks, and returns the cleaned text for each URL.
     */
    @Test
    void testGetUrlsTextsWithProvidedUrls() throws Exception {
        String url1 = "https://www.test1.com";
        String url2 = "https://www.test2.com";
        String originalContent1 = "Original Content 1";
        String originalContent2 = "Original Content 2";
        String cleanedContent1 = "Cleaned Content 1";
        String cleanedContent2 = "Cleaned Content 2";

        Mockito.when(urlService.getTextFromUrl(url1)).thenReturn(originalContent1);
        Mockito.when(urlService.getTextFromUrl(url2)).thenReturn(originalContent2);
        Mockito.when(webContentService.getTextFromUrl(originalContent1)).thenReturn(cleanedContent1);
        Mockito.when(webContentService.getTextFromUrl(originalContent2)).thenReturn(cleanedContent2);

        mockMvc.perform(get("/classifier/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"" + url1 + "\", \"" + url2 + "\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['" + url1 + "']", is(cleanedContent1)))
                .andExpect(jsonPath("$.['" + url2 + "']", is(cleanedContent2)));
    }

    /**
     * Test description:
     * This test verifies that when the input list of URLs is empty, the controller uses the default example URL,
     * processes it, and returns the cleaned content.
     */
    @Test
    void testGetUrlsTextsWithEmptyInput() throws Exception {
        String exampleUrl = "https://www.example.com";
        String originalContent = "Default Original Content";
        String cleanedContent = "Default Cleaned Content";

        Mockito.when(urlService.getTextFromUrl(exampleUrl)).thenReturn(originalContent);
        Mockito.when(webContentService.getTextFromUrl(originalContent)).thenReturn(cleanedContent);

        mockMvc.perform(get("/classifier/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['" + exampleUrl + "']", is(cleanedContent)));
    }
}