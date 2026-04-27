package com.cosmo.app;

import java.util.List;
import java.util.Scanner;

import com.cosmo.app.models.Book;
import com.cosmo.app.models.User;
import com.cosmo.app.repositories.InMemoryBookRepository;
import com.cosmo.app.repositories.InMemoryUserRepository;
import com.cosmo.app.services.BookService;
import com.cosmo.app.services.UserService;

public class App {
    private final UserService userService;
    private final BookService bookService;

    public App(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        UserService userService = new UserService(new InMemoryUserRepository());
        BookService bookService = new BookService(new InMemoryBookRepository());

        new DataSeeder(bookService, userService).seed();

        new App(userService, bookService).navigationMenu();
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
                () -> System.out.println("Utente non trovato.\n"));
    }

    private void printBooks() {
        System.out.println("Tutti i libri:");
        bookService.getAll()
                .forEach(entry -> System.out.println(entry + "\n"));
    }

    private void addBook() {
        // Todo: raccogliere i dati dal menu
        System.out.println("Inserimento libro in corso...");
        Book book = new Book("Guardiani della galassia", "Autore Sconosciuto", 1999);
        bookService.addBook(book);
        System.out.println("Libro aggiunto con successo!");
        System.out.println(book + "\n");
    }

    // private void removeBook() {
    // Book firstBook = bookService.getAll().get(0);
    // System.out.println("rimuovo un libro");
    // bookService.removeBook(firstBook.getIsbn());
    // }
}
