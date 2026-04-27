package com.cosmo.app.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.User;

/**
 * Implementazione in-memory del UserRepository.
 *
 * Struttura identica a InMemoryBookRepository: lista interna + stream API.
 * La ripetizione è voluta: ogni repository gestisce il proprio tipo di entità
 * in modo indipendente. Questo evita accoppiamenti indesiderati tra User e Book.
 */
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    /**
     * Cerca per email usando stream + filter + findFirst.
     * La lambda "u -> email.equals(u.getEmail())" è una funzione anonima
     * che riceve un utente e restituisce true se la sua email corrisponde.
     *
     * Nota: usiamo email.equals(u.getEmail()) invece di u.getEmail().equals(email)
     * perché se email fosse null il secondo form lancerebbe NullPointerException.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        // Copia difensiva: il chiamante non può alterare la lista interna
        return List.copyOf(users);
    }
}
