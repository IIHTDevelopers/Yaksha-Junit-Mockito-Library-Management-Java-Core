package com.elibrary.service;

import java.util.List;

import com.elibrary.exception.BookNotFoundException;
import com.elibrary.exception.ISBNAlreadyExistsException;
import com.elibrary.models.Book;
import com.elibrary.repository.BookRepository;

public class BookService {

	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public void addBook(Book book) throws ISBNAlreadyExistsException {
		bookRepository.addBook(book);
	}

	public List<Book> searchBookByName(String name) {
		return bookRepository.searchByName(name);
	}

	public List<Book> searchBookByAuthor(String author) {
		return bookRepository.searchByAuthor(author);
	}

	public List<Book> searchBookByPublisher(String publisher) {
		return bookRepository.searchByPublisher(publisher);
	}

	public boolean checkBookAvailability(String isbn) {
		return bookRepository.checkAvailability(isbn);
	}

	public void updateBook(String isbn, Book updatedBook) throws BookNotFoundException {
		bookRepository.updateBook(isbn, updatedBook);
	}

	public void deleteBook(String isbn) throws BookNotFoundException {
		bookRepository.deleteBook(isbn);
	}
}
