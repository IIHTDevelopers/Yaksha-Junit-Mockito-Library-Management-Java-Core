package com.elibrary;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.elibrary.exception.BookNotFoundException;
import com.elibrary.exception.ISBNAlreadyExistsException;
import com.elibrary.models.Book;
import com.elibrary.repository.BookRepository;
import com.elibrary.service.BookService;

public class LibraryManagementApp {
	private static Scanner scanner = new Scanner(System.in);
	private static BookService bookService = new BookService(new BookRepository());

	public static void main(String[] args) {
		while (true) {
			displayMainMenu();
			int choice = getUserChoice();

			switch (choice) {
			case 1:
				addBook();
				break;
			case 2:
				searchBookByName();
				break;
			case 3:
				searchBookByAuthor();
				break;
			case 4:
				searchBookByPublisher();
				break;
			case 5:
				checkBookAvailability();
				break;
			case 6:
				updateBook();
				break;
			case 7:
				deleteBook();
				break;
			case 8:
				exit();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void displayMainMenu() {
		System.out.println("Library Management System");
		System.out.println("1. Add a book");
		System.out.println("2. Search book by name");
		System.out.println("3. Search book by author");
		System.out.println("4. Search book by publisher");
		System.out.println("5. Check availability");
		System.out.println("6. Update book");
		System.out.println("7. Delete book");
		System.out.println("8. Exit");
	}

	private static int getUserChoice() {
		System.out.print("Enter your choice: ");
		return scanner.nextInt();
	}

	private static void addBook() {
		try {
			Book newBook = readBookDetails();
			bookService.addBook(newBook);
			displayBooks(bookService.searchBookByName(newBook.getTitle()));
		} catch (ISBNAlreadyExistsException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void searchBookByName() {
		scanner.nextLine();
		System.out.print("Enter book name: ");
		String bookName = scanner.nextLine();
		List<Book> booksByName = bookService.searchBookByName(bookName);
		displayBooks(booksByName);
	}

	private static void searchBookByAuthor() {
		scanner.nextLine();
		System.out.print("Enter author name: ");
		String authorName = scanner.nextLine();
		List<Book> booksByAuthor = bookService.searchBookByAuthor(authorName);
		displayBooks(booksByAuthor);
	}

	private static void searchBookByPublisher() {
		scanner.nextLine();
		System.out.print("Enter publisher name: ");
		String publisherName = scanner.nextLine();
		List<Book> booksByPublisher = bookService.searchBookByPublisher(publisherName);
		displayBooks(booksByPublisher);
	}

	private static void checkBookAvailability() {
		scanner.nextLine();
		System.out.print("Enter ISBN: ");
		String isbn = scanner.nextLine();
		boolean isAvailable = bookService.checkBookAvailability(isbn);
		System.out.println("Availability: " + (isAvailable ? "Available" : "Not Available"));
	}

	private static void updateBook() {
		scanner.nextLine();
		System.out.print("Enter ISBN to update: ");
		String updateIsbn = scanner.nextLine();
		try {
			Book updatedBook = readBookDetails();
			bookService.updateBook(updateIsbn, updatedBook);
			displayBooks(bookService.searchBookByName(updatedBook.getTitle()));
		} catch (BookNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void deleteBook() {
		scanner.nextLine();
		System.out.print("Enter ISBN to delete: ");
		String deleteIsbn = scanner.nextLine();
		try {
			bookService.deleteBook(deleteIsbn);
			System.out.println("Book successfully deleted.");
		} catch (BookNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void exit() {
		System.exit(0);
	}

	private static Book readBookDetails() {
		System.out.print("Enter ISBN: ");
		scanner.nextLine();
		String isbn = scanner.nextLine();
		System.out.print("Enter title: ");
		String title = scanner.nextLine();
		System.out.print("Enter author: ");
		String author = scanner.nextLine();
		System.out.print("Enter publisher: ");
		String publisher = scanner.nextLine();
		System.out.print("Is available (true/false): ");
		boolean isAvailable = scanner.nextBoolean();
		scanner.nextLine(); // Consume the newline character
		System.out.print("Enter issued date (yyyy-MM-dd): ");
		String issuedDateStr = scanner.nextLine();
		System.out.print("Enter due date (yyyy-MM-dd): ");
		String dueDateStr = scanner.nextLine();

		LocalDate issuedDate = LocalDate.parse(issuedDateStr);
		LocalDate dueDate = LocalDate.parse(dueDateStr);

		return new Book(isbn, title, author, publisher, isAvailable, issuedDate, dueDate);
	}

	private static void displayBooks(List<Book> books) {
		if (books.isEmpty()) {
			System.out.println("No books found.");
		} else {
			System.out.println("Books found:");
			for (Book book : books) {
				System.out.println(book);
			}
		}
	}
}
