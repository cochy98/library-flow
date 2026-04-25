package com.cosmo.app;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.cosmo.app.models.Book;
import com.cosmo.app.models.User;
import com.cosmo.app.repositories.InMemoryBookRepository;
import com.cosmo.app.repositories.InMemoryUserRepository;
import com.cosmo.app.services.BookService;
import com.cosmo.app.services.UserService;

/**
 * Hello world!
 */
public class App {
    UserService userService = new UserService(new InMemoryUserRepository());
    BookService bookService = new BookService(new InMemoryBookRepository());

    public static void main(String[] args) {
        App app = new App();

        // Inizializzo gli utenti
        app.initUsers();

        // Inizializzo i libri
        app.initBooks();

        // Apro il menu
        app.navigationMenu();

    }

    private void navigationMenu() {
        System.out.println("Benvenuto in libreria da cochy!\n");
        help();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                if (!scanner.hasNextLine()) {
                    System.out.println("Input terminato. Uscita.");
                    break;
                }

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }
                char carattere = input.charAt(0);

                switch (carattere) {
                    case '1':
                        addUser(scanner);
                        break;
                    case '2':
                        addBook();
                        break;
                    case '3':
                        printUsers();
                        break;
                    case '4':
                        printBooks();
                        break;
                    case '5':
                        printUser(scanner);
                        break;
                    // case '6':
                    // removeUser(scanner);
                    // break;
                    // case '7':
                    // break;
                    case '9':
                        help();
                        break;
                    case '0':
                        System.out.println("Uscita dal menu");
                        running = false;
                        break;
                    default:
                        System.out.println("Scelta non valida!");
                        break;
                }
            }
        }
    }

    private static void help() {
        String helpString = """
                HELP MENU:
                \t1 per inserire un utente;
                \t2 per inserire un libro;
                \t3 per ottenere la lista utenti;
                \t4 per ottenere la lista dei libri;
                \t5 per ricercare un utente;
                \t9 per ripetere questo messaggio;
                \t0 per uscire.
                """;
        System.out.println(helpString);
    }

    private void initUsers() {
        userService.addUser(new User("Pinco", "Pallino", "pincopallino@test.com"));
    }

    private void printUsers() {
        System.out.println("Tutti gli utenti:");
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
    }

    private void addUser(Scanner scanner) {
        System.out.println("\nInserimento utente");

        System.out.print("Nome: ");
        String name = scanner.nextLine().trim();

        System.out.print("Cognome: ");
        String surname = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        try {
            User user = new User(name, surname, email);
            userService.addUser(user);
            System.out.println("Utente aggiunto con successo!");
            System.out.println(user + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage() + "\n");
        }
    }

    private void printUser(Scanner scanner) {
        System.out.print("Inserisci email: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            System.out.println("Campo email obbligatorio!\n");
            return;
        }

        userService.getUser(email).ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Utente non trovato.\n")
        );
    }

    private void initBooks() {
        String[] bookTitles = {
                "Ombre Vive",
                "Cuore Nero",
                "Luce Spenta",
                "Senza Nome",
                "Ultimo Respiro",
                "Vento Freddo",
                "Anime Perse",
                "Oltre il Buio",
                "Tempo Spezzato",
                "Filo Invisibile"
        };

        String[] authors = {
                "Italo Calvino",
                "Umberto Eco",
                "Elsa Morante",
                "Alessandro Manzoni",
                "Dante Alighieri",
                "Primo Levi",
                "Virginia Woolf",
                "George Orwell",
                "Jane Austen",
                "Haruki Murakami"
        };

        int[] availableYears = { 1980, 1993, 2001, 2007, 1996, 2013, 2025, 2022, 2011 };

        Random rand = new Random();

        // for (String title : bookTitles) {
        for (int i = 0; i < bookTitles.length; i++) {
            String title = bookTitles[i];
            String author = authors[i];
            int index = rand.nextInt(availableYears.length);
            int year = availableYears[index];
            bookService.addBook(new Book(title, author, year));
        }
    }

    private void printBooks() {
        System.out.println("Tutti i libri:");
        bookService.getAll()
                .forEach(entry -> System.out.println(entry + "\n"));
    }

    private void addBook() {
        // Todo per adesso aggiungo un libro statico
        System.out.println("Inserimento libro in corso...");
        Book book = new Book("Guardiani della galassia", "Autore Sconosciuto", 1999);
        bookService.addBook(book);
        System.out.println("Libro aggiunto con successo!");
        System.out.println(book.toString() + "\n");
    }

    // private void removeBook() {
    // Book firstBook = bookService.getAll().get(0);
    // System.out.println("rimuovo un libro");
    // bookService.removeBook(firstBook.getIsbn());
    // }
}
