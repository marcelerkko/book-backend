package com.application.book.controller;

import com.application.book.exception.MySQLiteException;
import com.application.book.model.Book;
import com.application.book.model.Year;
import com.application.book.service.BookService;
import com.application.book.exception.IllegalEntityException;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<?> getBooks(
            @RequestParam (required = false) String author
            , @RequestParam (required = false) String year
            , @RequestParam (required = false) String publisher) {



        List<Book> books = bookService.getAllBooks();

        // filter list by author
        if (author != null) {
            books = books.stream().filter( book -> book.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
        }
        // filter list by year
        if (year != null) {
            try {
                Integer intYear = Integer.parseInt(year);
                books = books.stream().filter( book -> book.getYear().equals(intYear)).collect(Collectors.toList());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Bad year parameter");
            }
        }
        // filter list by publisher
        if (publisher != null) {
            books = books.stream().filter( book -> book.getPublisher() != null && book.getPublisher().equalsIgnoreCase(publisher)).collect(Collectors.toList());
        }

        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") String id) {
        try {
            Integer intId = Integer.parseInt(id);
            Book book = bookService.getBook(intId);

            if (book != null) {
                return ResponseEntity.ok().body(book);
            }
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/books")
    public ResponseEntity<?> postBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
        } catch (IllegalEntityException | MySQLiteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        JSONObject json = new JSONObject()
                .put("id", book.getId());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json.toString());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id) {
        try {
            Integer intId = Integer.parseInt(id);
            if (bookService.deleteBook(intId)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
