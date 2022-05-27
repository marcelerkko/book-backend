package com.application.book.service;

import com.application.book.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByAuthor(@Param("author") String author);
    List<Book> findByYear(@Param("year") Integer year);
    List<Book> findByPublisher(@Param("publisher") String publisher);
}
