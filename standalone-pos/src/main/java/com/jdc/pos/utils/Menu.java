package com.jdc.pos.utils;

public enum Menu {
    Home("POS Dashboard"),
    Pos("Tiny POS"),
    Sales("Sales History"),
    Category("Category Management"),
    Product("Product Management");

    private String title;

    Menu(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getFxml() {
        return String.format("%s.fxml", name());
    }
}
