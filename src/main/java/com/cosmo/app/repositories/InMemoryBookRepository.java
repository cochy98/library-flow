package com.cosmo.app.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.Book;

/**
 * Implementazione in-memory del BookRepository.
 *
 * "In-memory" significa che i dati vengono salvati in una lista Java (RAM)
 * e persi quando il programma termina. È l'implementazione più semplice
 * possibile: ottima per sviluppo, test, e per imparare il pattern senza
 * dover configurare un database.
 *
 * Quando in futuro si vorrà aggiungere un database, basterà creare una
 * nuova classe (es. SqlBookRepository) che implementa la stessa interfaccia.
 * Il resto del codice non cambierà.
 */
public class InMemoryBookRepository implements BookRepository {

    // ArrayList è la struttura dati più comune per liste dinamiche in Java.
    // "final" qui significa che il riferimento alla lista non cambia, ma
    // il contenuto della lista può crescere o ridursi liberamente.
    private final List<Book> books = new ArrayList<>();

    @Override
    public void save(Book book) {
        books.add(book);
    }

    /**
     * Stream API: introdotta in Java 8, permette di elaborare collezioni
     * in modo dichiarativo (dici COSA vuoi, non COME farlo).
     *
     * .stream()       → trasforma la lista in uno stream di elementi
     * .filter(...)    → mantiene solo i libri che soddisfano la condizione
     * .findFirst()    → prende il primo risultato, restituendo un Optional
     */
    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.stream()
                .filter(b -> isbn.equals(b.getIsbn()))
                .findFirst();
    }

    /**
     * List.copyOf() restituisce una copia immutabile della lista.
     * Questo è "defensive copying": il chiamante riceve una snapshot dei dati
     * e non può modificare la lista interna del repository per sbaglio.
     * È una buona pratica per proteggere lo stato interno dell'oggetto.
     */
    @Override
    public List<Book> findAll() {
        return List.copyOf(books);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().equals(author))
                .toList(); // equivalente a .collect(Collectors.toUnmodifiableList()) da Java 16
    }

    /**
     * removeIf() rimuove tutti gli elementi che soddisfano il predicato.
     * Restituisce true se almeno un elemento è stato rimosso, false altrimenti.
     * È più conciso e leggibile di un ciclo for con Iterator.
     */
    @Override
    public boolean deleteByIsbn(String isbn) {
        return books.removeIf(b -> b.getIsbn().equals(isbn));
    }

    @Override
    public boolean delete(Book book) {
        // equals() di Book confronta per ISBN, quindi questo funziona correttamente
        // anche se l'oggetto "book" passato è una istanza diversa in memoria
        return books.removeIf(b -> b.equals(book));
    }
}
