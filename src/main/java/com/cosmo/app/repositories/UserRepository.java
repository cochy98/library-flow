package com.cosmo.app.repositories;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.User;

/**
 * Interfaccia del Repository per gli utenti.
 *
 * Stessa filosofia di BookRepository: definisce il contratto (cosa si può fare)
 * senza specificare come (dove e come i dati vengono salvati).
 *
 * Nota: questa interfaccia è più semplice di BookRepository perché il dominio
 * degli utenti ha meno operazioni. Le interfacce devono essere minime: esporre
 * solo ciò che è necessario (principio "Interface Segregation" di SOLID).
 */
public interface UserRepository {

    void save(User user);

    /**
     * La ricerca per email usa Optional perché un utente con quella email
     * potrebbe non esistere. Restituire null sarebbe pericoloso.
     */
    Optional<User> findByEmail(String email);

    List<User> findAll();
}
