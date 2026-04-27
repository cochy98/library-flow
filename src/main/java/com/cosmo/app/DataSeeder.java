package com.cosmo.app;

import java.util.List;
import java.util.Random;

import com.cosmo.app.models.Book;
import com.cosmo.app.models.User;
import com.cosmo.app.services.BookService;
import com.cosmo.app.services.UserService;

/**
 * Classe responsabile di popolare l'applicazione con dati iniziali di esempio.
 *
 * DATA SEEDER PATTERN: un seeder è una classe dedicata a inserire dati di test
 * o dati iniziali (seed = seme). Separare questa logica da App.java mantiene
 * il codice principale pulito e rende facile aggiungere/modificare i dati
 * di esempio senza toccare la logica dell'applicazione.
 *
 * In framework come Spring Boot esiste un'equivalente (@DataJpaTest, CommandLineRunner),
 * ma il concetto di base è lo stesso.
 */
public class DataSeeder {
    private final BookService bookService;
    private final UserService userService;

    public DataSeeder(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    // Punto di ingresso pubblico: coordina il seeding di utenti e libri
    public void seed() {
        seedUsers();
        seedBooks();
    }

    private void seedUsers() {
        // List.of() crea una lista IMMUTABILE: non si può aggiungere né rimuovere
        // elementi dopo la creazione. È la scelta giusta qui perché la lista
        // di utenti di esempio è fissa e non deve essere modificata.
        List<User> users = List.of(
                new User("Pinco", "Pallino", "pincopallino@test.com"),
                new User("Mario", "Rossi", "mariorossi@test.com"),
                new User("Andrea", "Lucci", "andrealucci@test.com"),
                new User("Mirco", "Giordano", "mircogiordano@test.com"),
                new User("Marco", "Amerigo", "marcoamerigo@test.com")
            );

        // forEach con lambda: equivalente al classico for-each ma in stile funzionale.
        // Nota: qui si potrebbe usare il method reference "userService::addUser"
        // (forma ancora più compatta), ma la lambda esplicita è più leggibile per chi
        // incontra questo stile per la prima volta.
        users.forEach(u -> userService.addUser(u));
    }

    private void seedBooks() {
        String[] titles = {
                "Ombre Vive", "Cuore Nero", "Luce Spenta", "Senza Nome",
                "Ultimo Respiro", "Vento Freddo", "Anime Perse",
                "Oltre il Buio", "Tempo Spezzato", "Filo Invisibile"
        };

        String[] authors = {
                "Italo Calvino", "Umberto Eco", "Elsa Morante", "Alessandro Manzoni",
                "Dante Alighieri", "Primo Levi", "Virginia Woolf",
                "George Orwell", "Jane Austen", "Haruki Murakami"
        };

        int[] years = { 1980, 1993, 2001, 2007, 1996, 2013, 2025, 2022, 2011 };

        // Random: genera numeri pseudocasuali. Usato qui per assegnare anni
        // in modo casuale ai libri, rendendo i dati di esempio più realistici.
        // rand.nextInt(years.length) genera un indice tra 0 (incluso) e
        // years.length (escluso), quindi sempre un indice valido dell'array.
        Random rand = new Random();

        for (int i = 0; i < titles.length; i++) {
            bookService.addBook(new Book(titles[i], authors[i], years[rand.nextInt(years.length)]));
        }
    }
}
