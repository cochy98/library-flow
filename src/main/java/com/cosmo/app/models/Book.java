package com.cosmo.app.models;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private int pubblicationYear;
    private double price;
    private int availableCopies;

    public Book(String isbn, String title, String author, String genre, int pubblicationYear, double price,
            int availableCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pubblicationYear = pubblicationYear;
        this.price = price;
        this.availableCopies = availableCopies;
    }

    public Book(String title, String author, int pubblicationYear) {
        this.isbn = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.genre = "NC";
        this.pubblicationYear = pubblicationYear;
        this.price = 0;
        this.availableCopies = 1;
    }

    // Getter
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPubblicationYear() {
        return pubblicationYear;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    // Setter
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPubblicationYear(int pubblicationYear) {
        this.pubblicationYear = pubblicationYear;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return "Isbn: " + isbn + "\n" +
                "Titolo: " + title + "\n" +
                "Autore: " + author + "\n" +
                "Genere: " + genre + "\n" +
                "Anno pubblicazione: " + pubblicationYear + "\n" +
                "Prezzo: " + price + "\n" +
                "Copie disponibili: " + availableCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true; // stesso riferimento
        if (o == null || getClass() != o.getClass())
            return false; // null o classe diversa
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn); // confronto per ISBN
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}

// public enum Genre {
// THRILLER, FANTASY, ROMANCE, SCIENCE_FICTION, NON_FICTION
// }
