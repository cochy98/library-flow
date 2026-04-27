package com.cosmo.app.models;

import java.util.Objects;
import java.util.UUID;

/**
 * Modello che rappresenta un libro nella libreria.
 *
 * Questo è un esempio di classe POJO (Plain Old Java Object): contiene solo
 * campi privati, costruttori, getter e setter. È una delle strutture fondamentali
 * in Java per rappresentare entità del dominio.
 */
public class Book {

    // "final" su isbn significa che questo campo non può mai essere riassegnato
    // dopo la costruzione dell'oggetto. È giusto usarlo per un identificatore univoco
    // perché l'ISBN di un libro non deve mai cambiare.
    private final String isbn;

    // I campi privati implementano il principio di INCAPSULAMENTO: l'accesso ai
    // dati avviene solo attraverso i metodi getter/setter, non direttamente.
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private double price;
    private int availableCopies;

    /**
     * Costruttore completo: richiede tutti i dati del libro.
     * Usato tipicamente quando i dati provengono da una sorgente esterna
     * (es. database, file) dove l'ISBN è già noto.
     */
    public Book(String isbn, String title, String author, String genre, int publicationYear, double price,
            int availableCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.price = price;
        this.availableCopies = availableCopies;
    }

    /**
     * Costruttore semplificato: genera l'ISBN automaticamente con UUID.
     *
     * CONSTRUCTOR OVERLOADING: Java permette più costruttori con firme diverse.
     * Questo pattern è utile per offrire comodità senza duplicare logica.
     *
     * UUID.randomUUID() genera un identificatore univoco universale a 128 bit:
     * la probabilità di collisione è astronomicamente bassa, quindi è sicuro
     * usarlo come chiave univoca senza un database.
     */
    public Book(String title, String author, int publicationYear) {
        this.isbn = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.genre = "NC"; // Non Classificato: valore di default
        this.publicationYear = publicationYear;
        this.price = 0;
        this.availableCopies = 1;
    }

    // --- GETTER ---
    // I getter espongono i dati in sola lettura. Seguono la convenzione JavaBean:
    // "get" + NomeCampo con la prima lettera maiuscola (es. getIsbn, getTitle).
    // Questo schema è riconosciuto da molti framework Java (Spring, Hibernate, ecc.).

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

    public int getPublicationYear() {
        return publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    // --- SETTER ---
    // I setter permettono di modificare i campi in modo controllato.
    // Nota: isbn non ha un setter perché è "final": non può essere cambiato.

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    // Metodi di dominio: logica che ha senso solo in relazione al libro stesso.
    // È buona pratica tenere questi comportamenti nel modello invece di spargerli
    // nei service, quando riguardano esclusivamente lo stato dell'oggetto.

    public void decrementAvailableCopies() {
        availableCopies -= 1;
    }

    public void incrementAvailableCopies() {
        availableCopies += 1;
    }

    public boolean isAvailable() {
        return availableCopies >= 1;
    }

    /**
     * @Override toString: restituisce una rappresentazione testuale dell'oggetto.
     *
     * Senza questo override, System.out.println(book) stamperebbe qualcosa come
     * "com.cosmo.app.models.Book@1a2b3c" (riferimento in memoria), che è inutile.
     * Sovrascrivendolo otteniamo un output leggibile.
     */
    @Override
    public String toString() {
        return "Isbn: " + isbn + "\n" +
                "Titolo: " + title + "\n" +
                "Autore: " + author + "\n" +
                "Genere: " + genre + "\n" +
                "Anno pubblicazione: " + publicationYear + "\n" +
                "Prezzo: " + price + "\n" +
                "Copie disponibili: " + availableCopies;
    }

    /**
     * @Override equals: definisce quando due oggetti Book sono "uguali".
     *
     * Per default, equals confronta i riferimenti in memoria (come ==).
     * Sovrascrivendolo diciamo che due libri sono uguali se hanno lo stesso ISBN,
     * indipendentemente da dove si trovano in memoria.
     * Questo è fondamentale per usare Book in strutture come List, Set o Map.
     *
     * Objects.equals(a, b) è null-safe: non lancia NullPointerException se
     * uno dei due valori è null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true; // stesso riferimento in memoria: sicuramente uguali
        if (o == null || getClass() != o.getClass())
            return false; // null o tipo diverso: sicuramente diversi
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn); // uguali se stesso ISBN
    }

    /**
     * @Override hashCode: deve essere sempre sovrascritto insieme a equals.
     *
     * REGOLA FONDAMENTALE: se due oggetti sono equals(), devono avere lo stesso
     * hashCode(). Violando questa regola, HashMap e HashSet si comportano in
     * modo imprevedibile. Objects.hash() calcola un hash coerente dai campi dati.
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}

// public enum Genre {
// THRILLER, FANTASY, ROMANCE, SCIENCE_FICTION, NON_FICTION
// }
