package com.cosmo.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.Book;

public class BookService {
    // Questo è lo "stato" del servizio: la tua lista di libri
    private List<Book> books;

    // Il costruttore serve a inizializzare la lista appena crei il servizio
    public BookService() {
        this.books = new ArrayList<>();
    }

    // Metodo per aggiungere
    public void addBook(Book book) {
        this.books.add(book);
    }

    // public List<Book> findBooksByTitle(String title) {
    // }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream().filter(b -> b.getAuthor().equals(author)).toList();
    }

    public Optional<Book> findBook(String isbn) {
        return books.stream()
                .filter(b -> isbn.equals(b.getIsbn()))
                .findFirst();
    }

    public boolean removeBook(String isbn) {
        return books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    public List<Book> getAll() {
        return List.copyOf(books);
    }

    public List<Book> getBookAvailable() {
        return books.stream()
                .filter(Book::isAvailable)
                .toList();
    }

}
