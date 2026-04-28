package com.cosmo.app;

import java.util.List;

import com.cosmo.app.models.Book;
import com.cosmo.app.models.User;
import com.cosmo.app.repositories.InMemoryBookRepository;
import com.cosmo.app.repositories.InMemoryUserRepository;
import com.cosmo.app.services.BookService;
import com.cosmo.app.services.UserService;
import com.cosmo.app.utils.ConsoleReader;
import com.cosmo.app.utils.InputReader;


/**
 * Classe principale dell'applicazione library-flow.
 *
 * Contiene il metodo main() (punto di ingresso JVM) e gestisce il menu
 * di navigazione da console. Tiene i riferimenti ai service e delega
 * tutta la logica di business a essi.
 */
public class App {

    // I service sono campi d'istanza: ogni oggetto App ha i propri service.
    // "final" garantisce che i riferimenti non vengano mai riassegnati dopo
    // la costruzione, rendendo l'oggetto più prevedibile.
    private final UserService userService;
    private final BookService bookService;
    private final InputReader reader;

    public App(UserService userService, BookService bookService, InputReader reader) {
        this.userService = userService;
        this.bookService = bookService;
        this.reader = reader;
    }


    /**
     * Punto di ingresso dell'applicazione (entry point).
     *
     * La JVM cerca sempre un metodo con questa firma esatta:
     * public static void main(String[] args)
     * "static" è necessario perché la JVM lo chiama senza istanziare la classe.
     *
     * Qui avviene il "wiring" manuale: creiamo le implementazioni concrete
     * e le iniettiamo nei service. In framework come Spring questo viene fatto
     * automaticamente dall'IoC Container.
     */
    public static void main(String[] args) {
        UserService userService = new UserService(new InMemoryUserRepository());
        BookService bookService = new BookService(new InMemoryBookRepository());

        // Il seeder popola il sistema con dati di esempio prima di avviare il menu
        new DataSeeder(bookService, userService).seed();

        // Creiamo l'istanza di App e avviamo il menu: da qui in poi tutto è
        // gestito tramite metodi d'istanza (non static)
        try (InputReader reader = new ConsoleReader()) {
            new App(userService, bookService, reader).navigationMenu();
        }
    }

    /**
     * Loop principale del menu di navigazione.
     *
     * Non apre più risorse qui: lo Scanner è incapsulato in InputReader,
     * che viene creato e chiuso in main() tramite try-with-resources.
     * Questo metodo si limita a leggere input e delegare ai metodi privati.
     *
     * Il flag "running" controlla il ciclo: quando l'utente preme 0, diventa
     * false e il while termina in modo pulito.
     */
    private void navigationMenu() {
        System.out.println("Benvenuto in libreria da cochy!\n");
        help();

        boolean running = true;
        while (running) {
            // Gestisce EOF (es. input reindirizzato da file o pipe)
            if (!reader.hasNextLine()) {
                System.out.println("Input terminato. Uscita.");
                break;
            }

            // readLine("") non mostra nessun prompt ma applica già trim() internamente,
            // sostituendo il precedente reader.nextLine().trim() che usava l'API raw.
            String input = reader.readLine("");
            if (input.isEmpty()) {
                continue; // salta le righe vuote e torna all'inizio del while
            }

            // Prendiamo solo il primo carattere: l'utente può digitare "1 " o "1abc"
            // e il programma leggerà comunque '1'
            char carattere = input.charAt(0);

            /**
             * SWITCH STATEMENT: scelta tra più casi in base al valore di una variabile.
             * È più leggibile di una lunga catena if/else if quando i casi sono discreti.
             * "break" è necessario per uscire dal case: senza di esso Java esegue
             * anche tutti i case successivi ("fall-through"), comportamento quasi
             * sempre indesiderato.
             * Il case "default" gestisce qualsiasi valore non previsto.
             *
             * Il try/catch esterno gestisce IllegalStateException: viene lanciata da
             * readInt() quando l'input finisce (EOF) mentre si aspetta un numero.
             * Centralizzare qui la gestione evita di dover aggiungere il catch in ogni
             * metodo che chiama readInt().
             */
            try {
                switch (carattere) {
                    case '1':
                        addUser();
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
                        printUser();
                        break;
                    // case '6':
                    // removeUser();
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
            } catch (IllegalStateException e) {
                System.out.println("Input terminato. Uscita.");
                running = false;
            }
        }
    }

    /**
     * TEXT BLOCK (Java 15+): la sintassi con triple virgolette """ permette
     * di scrivere stringhe multiriga senza concatenazioni o \n espliciti.
     * Il testo viene allineato automaticamente in base all'indentazione.
     * "\t" è il carattere di tabulazione.
     */
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

    /**
     * forEach con method reference: users.forEach(System.out::println)
     * è equivalente a: for (User u : users) { System.out.println(u); }
     * Il method reference System.out::println è più conciso e sfrutta
     * il toString() che abbiamo definito in User.
     */
    private void printUsers() {
        System.out.println("Tutti gli utenti:");
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
    }

    /**
     * Raccoglie i dati dell'utente da tastiera e li passa al service.
     *
     * TRY/CATCH: cattura l'IllegalArgumentException che UserService lancia
     * in caso di validazione fallita (campo vuoto o email duplicata).
     * Grazie a questa separazione, la UI non conosce le regole di business:
     * sa solo che se riceve un'eccezione deve mostrare il messaggio di errore.
     */
    private void addUser() {
        System.out.println("\nInserimento utente");

        String name = reader.readLine("Nome: ");

        String surname = reader.readLine("Cognome: ");

        String email = reader.readLine("Email: ");

        try {
            User user = new User(name, surname, email);
            userService.addUser(user);
            System.out.println("Utente aggiunto con successo!");
            System.out.println(user + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage() + "\n");
        }
    }

    /**
     * ifPresentOrElse(): metodo di Optional che gestisce entrambi i casi in
     * un'unica chiamata:
     * - se l'Optional contiene un valore, esegue il primo Consumer (stampa
     * l'utente)
     * - altrimenti esegue il Runnable (stampa "non trovato")
     * È più espressivo di if (optional.isPresent()) { ... } else { ... }
     */
    private void printUser() {
        String email = reader.readLine("Inserisci email: ");
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

    /**
     * Raccoglie i dati del libro da tastiera e lo aggiunge tramite il service.
     *
     * InputUtils.readInt() è un metodo statico di utilità: gestisce il caso in cui
     * l'utente inserisca un valore non numerico, ripetendo la richiesta finché
     * l'input non è valido. Questa logica è stata estratta qui perché potrebbe
     * servire in più punti dell'applicazione (DRY: Don't Repeat Yourself).
     *
     * Il try/catch intercetta le validazioni fallite lanciate da BookService,
     * mostrando il messaggio di errore senza far crashare il programma.
     */
    private void addBook() {
        System.out.println("\nInserimento libro");

        String title = reader.readLine("Titolo: ");

        String author = reader.readLine("Autore: ");

        String genre = reader.readLine("Genere: ");

        int publicationYear = reader.readInt("Anno di pubblicazione: ");

        try {
            Book book = new Book(title, author, genre, publicationYear);
            bookService.addBook(book);
            System.out.println("Libro aggiunto con successo!");
            System.out.println(book + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage() + "\n");
        }
    }

    // private void removeBook() {
    // Book firstBook = bookService.getAll().get(0);
    // System.out.println("rimuovo un libro");
    // bookService.removeBook(firstBook.getIsbn());
    // }
}
