package com.cosmo.app.utils;

import java.util.Scanner;

/**
 * Wrapper dello Scanner di sistema che semplifica la lettura da console.
 *
 * Implementa AutoCloseable per poter essere usato nel try-with-resources:
 * try (ConsoleReader reader = new ConsoleReader()) { ... }
 * Java chiamerà close() automaticamente alla fine del blocco, liberando
 * lo Scanner anche in caso di eccezione.
 *
 * Il vantaggio rispetto a usare Scanner direttamente è che i metodi di questa
 * classe nascondono i dettagli (trim, gestione EOF, loop per input non validi)
 * e offrono un'API più espressiva al chiamante.
 */
public class ConsoleReader implements InputReader {

    // private: nessuno fuori da questa classe deve accedere allo Scanner
    // direttamente.
    // final: il riferimento non cambia mai dopo la costruzione.
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Mostra un messaggio e legge la riga successiva, rimuovendo spazi iniziali
     * e finali con trim(). Restituisce una stringa vuota se l'input è EOF.
     */
    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        // hasNextLine() protegge da EOF (es. input reindirizzato da file o pipe)
        if (!scanner.hasNextLine())
            return "";
        return scanner.nextLine().trim();
    }

    /**
     * Legge un intero dalla console, ripetendo la richiesta finché l'utente
     * non inserisce un valore numerico valido.
     *
     * Integer.parseInt() lancia NumberFormatException se la stringa non è un
     * numero: il catch la intercetta e ripete il ciclo invece di far crashare
     * il programma.
     *
     * Se readLine() restituisce "" (EOF), lanciamo IllegalStateException invece
     * di ciclare all'infinito: parseInt("") continuerebbe a fallire per sempre.
     * IllegalStateException è unchecked: il chiamante può propagarla o catturarla
     * a seconda di quanto è a monte del call stack.
     */
    @Override
    public int readInt(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (input.isEmpty()) {
                throw new IllegalStateException("EOF: input terminato prima di ricevere un numero.");
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    /**
     * Chiude lo Scanner sottostante.
     * Questo metodo viene chiamato automaticamente da Java quando ConsoleReader
     * è usato nel try-with-resources, grazie all'interfaccia AutoCloseable.
     */
    @Override
    public void close() {
        scanner.close();
    }
}
