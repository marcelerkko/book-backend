package com.application.book.model;

public class Year {
    private String year;
    private Integer intYear;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        try {
            this.intYear = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            System.out.println("Invalid year");
        }
        this.year = year;
    }
}
