package com.example.weatherapp;

public enum ViewState {
    INDEX("index"),
    WEATHER("weather"),
    ERROR("error");

    private final String viewName;

    ViewState(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
