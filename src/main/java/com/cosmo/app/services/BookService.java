package com.cosmo.app.services;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.Book;
import com.cosmo.app.repositories.BookRepository;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    // Metodo per aggiungere
    public void addBook(Book book) {
        // Todo aggiungere i controlli prima del salvataggio
        repository.save(book);
    }

    // public List<Book> findBooksByTitle(String title) {
    // }

    public Optional<Book> findBook(String isbn) {
        return repository.findByIsbn(isbn);
    }

    public List<Book> findBooksByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    public boolean removeBook(String isbn) {
        Book book = repository.findByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Libro non trovato!"));

        return repository.delete(book);
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return repository.findAll().stream()
                .filter(Book::isAvailable)
                .toList();
    }
}
