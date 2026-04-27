# library-flow

Progetto didattico scritto in Java 17, con l'obiettivo di imparare e mettere in pratica i paradigmi moderni del linguaggio attraverso un caso concreto: la gestione di una piccola libreria.

## Scopo

Il progetto nasce per esercitarsi con i concetti fondamentali di Java moderno in modo graduale e pratico. Non si tratta di un'applicazione pronta per la produzione, ma di un ambiente di apprendimento dove ogni scelta implementativa riflette un concetto specifico da studiare.

## Cosa si impara

- **Separazione delle responsabilita** tramite l'architettura a strati: modelli, repository e servizi hanno ciascuno un ruolo preciso e non si sovrappongono.
- **Polimorfismo e interfacce**: `BookRepository` e `UserRepository` sono interfacce implementate da classi in-memory. Questo rende il codice intercambiabile e facilita la comprensione del principio di inversione delle dipendenze.
- **Dependency injection manuale**: i servizi ricevono il repository tramite costruttore, senza framework. Serve a capire cosa fa un framework come Spring prima di usarlo.
- **Optional**: usato in `getUser` e `findBook` per gestire l'assenza di un valore senza ricorrere a `null`.
- **Stream API**: usata per filtrare liste (`filter`, `toList`, `forEach`) in modo dichiarativo al posto dei cicli tradizionali.
- **Text block** (Java 15+): il menu di aiuto usa la sintassi `"""..."""` per stringhe multiriga leggibili.
- **equals e hashCode**: implementati su `Book` basandosi sull'ISBN, per capire come Java confronta gli oggetti.
- **UUID**: ogni libro riceve un identificatore univoco generato automaticamente.

## Struttura del progetto

```
src/main/java/com/cosmo/app/
    App.java                          punto di ingresso, menu interattivo da terminale
    models/
        Book.java                     modello libro con ISBN, titolo, autore, anno, prezzo, copie
        User.java                     modello utente con nome, cognome, email
    repositories/
        BookRepository.java           interfaccia per le operazioni sui libri
        UserRepository.java           interfaccia per le operazioni sugli utenti
        InMemoryBookRepository.java   implementazione che salva i libri in memoria (ArrayList)
        InMemoryUserRepository.java   implementazione che salva gli utenti in memoria
    services/
        BookService.java              logica di business per i libri
        UserService.java              logica di business per gli utenti, con validazioni
```

## Requisiti

- Java 17 o superiore
- Maven 3.x

## Come avviare

```bash
mvn compile exec:java -Dexec.mainClass="com.cosmo.app.App"
```

Oppure eseguire direttamente dalla classe `App` con il proprio IDE.

## Come eseguire i test

```bash
mvn test
```

## Funzionalita disponibili

L'applicazione avvia un menu interattivo da terminale con le seguenti opzioni:

- Aggiungere un utente (con nome, cognome ed email)
- Aggiungere un libro
- Visualizzare la lista di tutti gli utenti
- Visualizzare la lista di tutti i libri
- Cercare un utente per email

I dati vengono mantenuti solo in memoria e non persistono tra un'esecuzione e l'altra. Questo e intenzionale: l'obiettivo attuale e la struttura del codice, non la persistenza.

## Prossimi passi possibili

- Sostituire il menu testuale con un'interfaccia grafica in JavaFX
- Aggiungere la persistenza su file o database
- Introdurre un layer di eccezioni personalizzate
- Scrivere test unitari per i servizi
