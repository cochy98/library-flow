package com.cosmo.app.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.Book;

public class InMemoryBookRepository implements BookRepository {
    private final List<Book> books = new ArrayList<>();

    @Override
    public void save(Book book) {
        books.add(book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.stream()
                .filter(b -> isbn.equals(b.getIsbn()))
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return List.copyOf(books);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().equals(author))
                .toList();
    }

    @Override
    public boolean deleteByIsbn(String isbn) {
        return books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    @Override
    public boolean delete(Book book) {
        return books.removeIf(b -> b.equals(book));
    }
}
