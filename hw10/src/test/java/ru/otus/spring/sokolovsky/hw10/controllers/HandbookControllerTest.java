package ru.otus.spring.sokolovsky.hw10.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.sokolovsky.hw10.services.LibraryService;
import ru.otus.spring.sokolovsky.hw10.services.StatisticService;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(locations = {"/test-application.properties"})
public class HandbookControllerTest {

    @Test
    @DisplayName("List of genres and authors are taken")
    void gettingListOfGenresThenAuthors() throws Exception {
        LibraryService libraryService = mock(LibraryService.class);
        StatisticService staticService = mock(StatisticService.class);
        HandbookController controller = new HandbookController(
                libraryService, staticService
        );

        when(staticService.getGenreToBookStatistic())
                .thenReturn(new StatisticService.EntityStatistic(new HashMap<>()));

        when(staticService.getAuthorsToBookStatistic())
                .thenReturn(new StatisticService.EntityStatistic(new HashMap<>()));

        MockMvc rest = standaloneSetup(controller).build();
        rest.perform(get("/genre/list"))
                .andExpect(status().isOk());

        rest.perform(get("/author/list"))
                .andExpect(status().isOk());

        verify(libraryService).getGenres();
        verify(libraryService).getAuthors();
    }
}
