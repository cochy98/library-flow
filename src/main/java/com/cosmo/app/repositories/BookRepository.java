package com.cosmo.app.repositories;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.Book;

/**
 * Interfaccia del Repository per i libri.
 *
 * REPOSITORY PATTERN: il repository astrae la sorgente dati (memoria, database,
 * file, API remota) e offre al resto dell'applicazione un contratto stabile
 * per leggere e scrivere entità. Chi usa il repository non sa, né gli importa
 * come i dati vengono effettivamente memorizzati.
 *
 * Perché un'interfaccia e non direttamente una classe?
 * - Permette di avere più implementazioni (InMemory per i test, SQL per la prod)
 * - Consente il testing con implementazioni fittizie (mock/stub)
 * - Segue il principio SOLID "Dependency Inversion": dipendi dall'astrazione,
 *   non dall'implementazione concreta
 */
public interface BookRepository {

    // void: non restituisce nulla, si limita a persistere il libro
    void save(Book book);

    /**
     * Optional<Book>: questo tipo segnala esplicitamente che il libro
     * POTREBBE non esistere. È preferibile a restituire null perché:
     * - Obbliga il chiamante a gestire il caso "non trovato"
     * - Elimina il rischio di NullPointerException se il chiamante dimentica
     *   di controllare il valore di ritorno
     */
    Optional<Book> findByIsbn(String isbn);

    // Restituisce una lista (possibilmente vuota, mai null per convenzione)
    List<Book> findAll();

    List<Book> findByAuthor(String author);

    // boolean: indica se la cancellazione ha avuto successo (true) o se
    // il libro non è stato trovato (false)
    boolean deleteByIsbn(String isbn);

    boolean delete(Book book);
}
