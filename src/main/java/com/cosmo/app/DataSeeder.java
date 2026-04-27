package com.cosmo.app;

import java.util.List;
import java.util.Random;

import com.cosmo.app.models.Book;
import com.cosmo.app.models.User;
import com.cosmo.app.services.BookService;
import com.cosmo.app.services.UserService;

public class DataSeeder {
    private final BookService bookService;
    private final UserService userService;

    public DataSeeder(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public void seed() {
        seedUsers();
        seedBooks();
    }

    private void seedUsers() {
        List<User> users = List.of(
                new User("Pinco", "Pallino", "pincopallino@test.com"),
                new User("Mario", "Rossi", "mariorossi@test.com"),
                new User("Andrea", "Lucci", "andrealucci@test.com"),
                new User("Mirco", "Giordano", "mircogiordano@test.com"),
                new User("Marco", "Amerigo", "marcoamerigo@test.com")
            );

        for (User user : users) {
            userService.addUser(user);
        }
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
        Random rand = new Random();

        for (int i = 0; i < titles.length; i++) {
            bookService.addBook(new Book(titles[i], authors[i], years[rand.nextInt(years.length)]));
        }
    }
}
