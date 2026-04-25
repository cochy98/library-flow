package com.cosmo.app.repositories;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.Book;

public interface BookRepository {
    void save(Book book);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findAll();

    List<Book> findByAuthor(String author);

    boolean deleteByIsbn(String isbn);

    boolean delete(Book book);
}
