package com.application.book.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
CREATE TABLE IF NOT EXISTS books
        (
        id integer PRIMARY KEY NOT NULL,
        title text NOT NULL,
        author text NOT NULL,
        year integer NOT NULL,
        publisher text,
        description text,

        CONSTRAINT unique_constraint UNIQUE (title, author, year)
        );
 */

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Integer id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="author", nullable = false)
    private String author;

    @Column(name="year", nullable = false)
    @Digits(integer = 5, fraction = 0)
    private BigDecimal year;

    @Column(name="publisher")
    private String publisher;
    @Column(name="description")
    private String description;

    public Book() { }

    public Book(String title, String author, BigDecimal year, String publisher, String description) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.publisher = publisher;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
