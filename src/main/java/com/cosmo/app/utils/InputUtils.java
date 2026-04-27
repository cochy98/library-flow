package com.cosmo.app.utils;

import java.util.Scanner;

/**
 * Classe di utilità per la lettura dell'input da console.
 *
 * UTILITY CLASS: una classe che contiene solo metodi statici di supporto,
 * senza stato interno. Non rappresenta un'entità del dominio (non è un Book
 * né un User), ma raccoglie funzionalità riusabili legate all'I/O.
 *
 * Il pacchetto "utils" è la convenzione standard per questo tipo di classi.
 */
public class InputUtils {

    /**
     * Costruttore privato: impedisce di creare istanze di questa classe.
     *
     * Poiché tutti i metodi sono statici, non ha senso creare oggetti InputUtils.
     * Rendere il costruttore privato lo comunica esplicitamente: questa classe
     * non si istanzia, si usa come "InputUtils.readInt(...)".
     * È un pattern comune nelle utility class (es. java.util.Collections, java.util.Arrays).
     */
    private InputUtils() {
        // evita istanziazione
    }

    /**
     * Legge un intero da console, ripetendo la richiesta finché l'input è valido.
     *
     * "while (true)" con "return" interno: loop infinito che si interrompe solo
     * quando l'utente inserisce un numero valido. È il pattern corretto per
     * "continua a chiedere finché non ottengo ciò che voglio".
     *
     * Integer.parseInt(): converte una stringa in int. Se la stringa non è un
     * numero (es. "abc"), lancia NumberFormatException — un'eccezione checked
     * che segnala un formato non valido. La catturiamo con catch per mostrare
     * un messaggio e riprovare invece di far crashare il programma.
     *
     * @param scanner lo Scanner condiviso con il chiamante (non ne creiamo uno nuovo
     *                per evitare di chiudere System.in accidentalmente)
     * @param prompt  il testo da mostrare all'utente prima di leggere l'input
     * @return l'intero inserito dall'utente
     */
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Valore non valido, riprova.");
            }
        }
    }

}
