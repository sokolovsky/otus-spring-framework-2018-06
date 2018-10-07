package ru.otus.spring.sokolovsky.hw13.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.sokolovsky.hw13.services.LibraryService;
import ru.otus.spring.sokolovsky.hw13.services.StatisticService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.base.path:}")
public class HandbookController {

    private LibraryService libraryService;
    private StatisticService statisticService;

    public HandbookController(LibraryService libraryService, StatisticService statisticService) {
        this.libraryService = libraryService;
        this.statisticService = statisticService;
    }

    @GetMapping("/genre/list")
    public Map<String, Object> genres() {
        Map<String, Object> map = new HashMap<>();
        map.put("genres", libraryService.getGenres());
        map.put("statistic", statisticService.getGenreToBookStatistic().getMap());
        return map;
    }

    @GetMapping("/author/list")
    public Map<String, Object> authors() {
        Map<String, Object> map = new HashMap<>();
        map.put("authors", libraryService.getAuthors());
        map.put("statistic", statisticService.getAuthorsToBookStatistic().getMap());
        return map;
    }
}
