package com.application.book.service.impl;

import com.application.book.model.Book;
import com.application.book.service.BookRepository;
import com.application.book.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book getBook(Integer id) {
        Optional<Book> optBook = bookRepository.findById(id);
        return optBook.orElse(null);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> getBooksByYear(Integer year) {
        return bookRepository.findByYear(year);
    }

    @Override
    public List<Book> getBooksByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher);
    }

    @Override
    public boolean addBook(Book book) {
        try {
            bookRepository.save(book);
        } catch (Exception e) { // PropertyValueException not properly caught?
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBook(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
