package com.elibrary.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elibrary.exception.BookNotFoundException;
import com.elibrary.exception.ISBNAlreadyExistsException;
import com.elibrary.models.Book;

public class BookRepository {

	private Map<String, Book> bookStorage = new HashMap<>();

	public void addBook(Book book) throws ISBNAlreadyExistsException {
		if (bookStorage.containsKey(book.getIsbn())) {
			throw new ISBNAlreadyExistsException("ISBN already exists.");
		}
		bookStorage.put(book.getIsbn(), book);
	}

	public List<Book> searchByName(String name) {
		List<Book> result = new ArrayList<>();
		for (Book book : bookStorage.values()) {
			if (book.getTitle().equalsIgnoreCase(name)) {
				result.add(book);
			}
		}
		return result;
	}

	public List<Book> searchByAuthor(String author) {
		List<Book> result = new ArrayList<>();
		for (Book book : bookStorage.values()) {
			if (book.getAuthor().equalsIgnoreCase(author)) {
				result.add(book);
			}
		}
		return result;
	}

	public List<Book> searchByPublisher(String publisher) {
		List<Book> result = new ArrayList<>();
		for (Book book : bookStorage.values()) {
			if (book.getPublisher().equalsIgnoreCase(publisher)) {
				result.add(book);
			}
		}
		return result;
	}

	public boolean checkAvailability(String isbn) {
		return bookStorage.containsKey(isbn) && bookStorage.get(isbn).isAvailable();
	}

	public void updateBook(String isbn, Book updatedBook) throws BookNotFoundException {
		if (!bookStorage.containsKey(isbn)) {
			throw new BookNotFoundException("Book not found for ISBN: " + isbn);
		}
		bookStorage.put(isbn, updatedBook);
	}

	public void deleteBook(String isbn) throws BookNotFoundException {
		if (!bookStorage.containsKey(isbn)) {
			throw new BookNotFoundException("Book not found for ISBN: " + isbn);
		}
		bookStorage.remove(isbn);
	}
}
