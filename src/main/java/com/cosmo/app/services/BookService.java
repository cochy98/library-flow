package com.cosmo.app.services;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.Book;
import com.cosmo.app.repositories.BookRepository;

/**
 * Service per la gestione dei libri.
 *
 * SERVICE LAYER: questo strato contiene la logica di business dell'applicazione.
 * Si trova tra il livello di presentazione (App.java) e quello di persistenza
 * (BookRepository). Ogni operazione significativa del dominio passa da qui.
 *
 * Perché separare Service e Repository?
 * - Il repository sa solo come salvare/leggere dati
 * - Il service sa quali regole di business applicare (validazioni, trasformazioni)
 * - Tenere le responsabilità separate rende il codice più facile da testare
 *   e da modificare in futuro
 */
public class BookService {

    /**
     * DEPENDENCY INJECTION via costruttore: il repository viene passato dall'esterno
     * anziché essere creato internamente. Questo approccio è fondamentale perché:
     * - Nei test si può passare un repository finto (mock) senza toccare dati reali
     * - L'implementazione concreta (InMemory, SQL...) può cambiare senza modificare
     *   questo service
     * - Rende esplicite le dipendenze della classe
     */
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    // Aggiunge un libro al repository. Il Todo indica che qui mancano ancora
    // validazioni (es. titolo non vuoto, anno valido, ecc.)
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

    /**
     * Rimuove un libro cercandolo prima per ISBN.
     *
     * orElseThrow(): se l'Optional è vuoto (libro non trovato), lancia
     * immediatamente un'eccezione con un messaggio chiaro. È preferibile
     * a controllare manualmente con isPresent() perché è più espressivo
     * e non lascia spazio a dimenticare il controllo.
     *
     * IllegalArgumentException: eccezione unchecked (non va dichiarata nella
     * firma del metodo) usata per segnalare argomenti non validi.
     */
    public boolean removeBook(String isbn) {
        Book book = repository.findByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Libro non trovato!"));

        return repository.delete(book);
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    /**
     * Restituisce solo i libri disponibili, usando Stream API.
     *
     * Book::isAvailable è un "method reference": equivale a scrivere
     * b -> b.isAvailable(), ma è più compatto e leggibile.
     * Si usa quando la lambda non fa altro che chiamare un metodo esistente.
     */
    public List<Book> getAvailableBooks() {
        return repository.findAll().stream()
                .filter(Book::isAvailable)
                .toList();
    }
}
