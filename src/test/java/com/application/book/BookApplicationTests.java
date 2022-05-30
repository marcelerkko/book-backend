package com.application.book;

import com.application.book.controller.BookController;
import com.application.book.model.Book;
import com.application.book.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookApplicationTests {
	MockMvc mockMvc;

	@InjectMocks
	private BookController bookController;

	@Mock
	private BookService bookService;

	@Before
	public void preTest() {
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void contextLoads() {
		assertThat(bookController).isNotNull();
		assertThat(bookService).isNotNull();
	}

	@Test
	public void requestPostBookOk() {
		String jsonBody = """
			{
			"title": "Harry Potter and the Philosophers Stone",
			"author": "J.K. Rowling",
			"year": 1997,
			"publisher": "Bloomsbury (UK)",
			"description": "A book about a wizard boy"
			}
			""";

		try {
			when(bookService.addBook(any(Book.class))).thenReturn(true);
			mockMvc.perform(post("/books")
				.content(jsonBody)
				.contentType("application/json")
				.characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	@Test
	public void requestPostBookBadTitle() {
		String jsonBody = """
			{
			"author": "J.K. Rowling",
			"year": 1997,
			"publisher": "Bloomsbury (UK)",
			"description": "A book about a wizard boy"
			}
			""";

		try {
			when(bookService.addBook(any(Book.class))).thenReturn(false);
			mockMvc.perform(post("/books")
					.content(jsonBody)
					.contentType("application/json")
					.characterEncoding("utf-8"))
					.andDo(print())
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	@Test
	public void requestPostBookBadAuthor() {
		String jsonBody = """
			{
			"title": "Harry Potter and the Philosophers Stone",
			"year": 1997,
			"publisher": "Bloomsbury (UK)",
			"description": "A book about a wizard boy"
			}
			""";

		try {
			when(bookService.addBook(any(Book.class))).thenReturn(false);
			mockMvc.perform(post("/books")
					.content(jsonBody)
					.contentType("application/json")
					.characterEncoding("utf-8"))
					.andDo(print())
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	@Test
	public void requestPostBookBadYear() {
		String jsonBody = """
			{
			"title": "Harry Potter and the Philosophers Stone",
			"author": "J.K. Rowling",
			"publisher": "Bloomsbury (UK)",
			"description": "A book about a wizard boy"
			}
			""";

		try {
			when(bookService.addBook(any(Book.class))).thenReturn(false);
			mockMvc.perform(post("/books")
					.content(jsonBody)
					.contentType("application/json")
					.characterEncoding("utf-8"))
					.andDo(print())
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	@Test
	public void getAllBooks() {
		String book1 = """
				{
				"title": "Harry Potter and the Philosophers Stone",
				"author": "J.K. Rowling",
				"year": 1997,
				"publisher": "Bloomsbury (UK)",
				"description": "A book about a wizard boy"
				}
			""";

		String book2 = """
				{
				 "title": "Old Testament",
				 "author": "Various",
				 "year": -165,
				 "description": "A holy book of Christianity and Jewish faith"
				 }
					 
			""";

		try {
			when(bookService.addBook(any(Book.class))).thenReturn(true);
			mockMvc.perform(post("/books")
					.content(book1)
					.contentType("application/json")
					.characterEncoding("utf-8"))
					.andDo(print())
					.andExpect(status().isOk());

			mockMvc.perform(post("/books")
					.content(book2)
					.contentType("application/json")
					.characterEncoding("utf-8"))
					.andDo(print())
					.andExpect(status().isOk());

			List<Book> books = new ArrayList<>();
			books.add(new Book(
					"Harry Potter and the Philosophers Stone",
					"J.K. Rowling",
					new BigDecimal(1997),
					"Bloomsbury (UK)",
					"A book about a wizard boy"));
			books.add(new Book(
					"Old Testament",
					"Various",
					new BigDecimal(-165),
					null,
					"A holy book of Christianity and Jewish faith"));

			when(bookService.getAllBooks()).thenReturn(books);
			mockMvc.perform(get("/books"))
					.andDo(print())
					.andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}


}
