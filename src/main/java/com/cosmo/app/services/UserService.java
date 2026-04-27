package com.cosmo.app.services;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.User;
import com.cosmo.app.repositories.UserRepository;

/**
 * Service per la gestione degli utenti.
 *
 * Contiene le regole di business relative agli utenti:
 * - validazione dei dati prima dell'inserimento
 * - controllo di unicità dell'email
 *
 * Le validazioni vivono qui (nel service) e non nel modello (User) né nel
 * repository, perché sono regole di business, non vincoli di struttura dati.
 */
public class UserService {

    // Dependency Injection via costruttore (vedi BookService per la spiegazione)
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /**
     * Aggiunge un utente dopo aver validato i dati.
     *
     * isBlank() vs isEmpty():
     * - isEmpty() restituisce true solo se la stringa ha lunghezza zero ("")
     * - isBlank() restituisce true anche per stringhe di soli spazi ("   ")
     * Usiamo isBlank() perché " " non è un nome valido.
     *
     * isPresent(): controlla se l'Optional contiene un valore. Qui verifichiamo
     * che non esista già un utente con la stessa email prima di salvare.
     *
     * Lanciare eccezioni nei service è il modo corretto per comunicare errori
     * al livello superiore (App.java), che li catturerà con try/catch.
     */
    public void addUser(User user) {
        if (user.getName().isBlank() || user.getSurname().isBlank() || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("tutti i campi sono obbligatori.");
        }
        if (getUser(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("email già registrata.");
        }
        repository.save(user);
    }

    public Optional<User> getUser(String email) {
        return repository.findByEmail(email);
    }
}
