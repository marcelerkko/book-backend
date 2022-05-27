package com.application.book;

import com.application.book.controller.BookController;
import com.application.book.model.Book;
import com.application.book.service.BookRepository;
import com.application.book.service.BookService;
import com.application.book.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookApplicationTests {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	BookServiceImpl bookService;

	@Test
	public void testAddBookSuccess() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//		when(bookService.addBook(any(Book.class))).thenReturn(true);
//
//		Book book = new Book("Harry Potter and the Philosophers Stone", "J.K. Rowling", 1997, "Bloomsbury (UK)", "A book about a wizard boy");
//		ResponseEntity<?> responseEntity = bookController.postBook(book);
//
//		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

		Book book = new Book("Harry Potter and the Philosophers Stone", "J.K. Rowling", 1997, "Bloomsbury (UK)", "A book about a wizard boy");
		Integer id = book.getId();
		when(restTemplate.getForEntity("http://localhost:9000/books/" + id, Book.class))
				.thenReturn(new ResponseEntity<>(book, HttpStatus.OK));

		Book getBook = bookService.getBook(id);
		assertEquals(book, getBook);
	}

//	@Test
//	public void testAddBookFail() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//		when(bookService.addBook(any(Book.class))).thenReturn(true);
//
//		Book book = new Book(null, null, null, "Bloomsbury (UK)", "A book about a wizard boy");
//		ResponseEntity<?> responseEntity = bookController.postBook(book);
//
//		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
//	}
}
