package ru.tylyakov.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Codes {
    SUCCESS("SUCCESS"),
    FAILED("failed");

    private final String name;

    Codes(String name){
        this.name = name;
    }

    @JsonValue
    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}
