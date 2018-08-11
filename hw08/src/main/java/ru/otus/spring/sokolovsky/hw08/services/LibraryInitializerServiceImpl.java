package ru.otus.spring.sokolovsky.hw08.services;

import org.springframework.stereotype.Service;

@Service
public class LibraryInitializerServiceImpl implements LibraryInitializerService {
    @Override
    public void init() {
        System.out.println("Lib was init.");
    }
}
