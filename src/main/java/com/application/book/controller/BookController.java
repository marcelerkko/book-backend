package com.application.book.controller;

import com.application.book.model.Book;
import com.application.book.service.BookService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            , @RequestParam (required = false) Integer year
            , @RequestParam (required = false) String publisher) {



        List<Book> books = bookService.getAllBooks();

        // filter list by author
        if (author != null) {
            if (author.length() == 0)
                return ResponseEntity.badRequest().build();
            books = books.stream().filter( book -> book.getAuthor().equals(author)).collect(Collectors.toList());;
        }
        // filter list by year
        if (year != null) {
            books = books.stream().filter( book -> book.getYear().equals(year)).collect(Collectors.toList());;
        }
        // filter list by publisher
        if (publisher != null) {
            if (publisher.length() == 0)
                return ResponseEntity.badRequest().build();
            books = books.stream().filter( book -> book.getPublisher().equals(publisher)).collect(Collectors.toList());;
        }

        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") Integer id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            return ResponseEntity.ok().body(book);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/books")
    public ResponseEntity<?> postBook(@RequestBody Book book) {
        if (bookService.addBook(book)) {
            JSONObject json = new JSONObject()
                    .put("id", book.getId());
            return ResponseEntity.ok().body(json.toString());
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Integer id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
