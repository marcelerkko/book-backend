package com.application.book.service;

import com.application.book.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBook(Integer id);

    // for if filtering should happen using a single parameter only
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByYear(Integer year);
    List<Book> getBooksByPublisher(String publisher);

    boolean addBook(Book book);

    boolean deleteBook(Integer id);
}
