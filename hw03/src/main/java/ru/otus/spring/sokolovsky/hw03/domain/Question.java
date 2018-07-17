package ru.otus.spring.sokolovsky.hw03.domain;

public class Question {

    private String description;
    private int rightVariantIndex;
    private String[] variants;

    public Question(String description, int rightVariantIndex, String[] variants) {
        this.description = description;
        this.rightVariantIndex = rightVariantIndex;
        this.variants = variants;
    }

    public String[] getVariants() {
        return variants;
    }

    public int getRightVariantNumber() {
        return rightVariantIndex;
    }

    public String getDescription() {
        return description;
    }
}
