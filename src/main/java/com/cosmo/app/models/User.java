package com.cosmo.app.models;

import java.util.Objects;

/**
 * Modello che rappresenta un utente della libreria.
 *
 * Classe POJO semplice: tre campi, costruttore, getter e setter.
 * L'email è usata come identificatore naturale (chiave univoca di dominio),
 * motivo per cui è il campo scelto per equals() e hashCode().
 */
public class User {

    // I campi non sono "final" qui perché i dati dell'utente possono cambiare
    // (es. cambio email, cambio cognome). Confronta con Book.isbn che è final.
    private String name;
    private String surname;
    private String email;

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // --- GETTER ---

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    // --- SETTER ---

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- OVERRIDE ---

    /**
     * Restituisce una stringa leggibile dell'utente.
     * Usato da System.out.println() e da qualsiasi contesto che converta
     * l'oggetto in stringa (es. concatenazione con +).
     */
    @Override
    public String toString() {
        return "Nome completo: " + this.name + " " + this.surname + "\n" +
                "Email: " + this.email;
    }

    /**
     * Due utenti sono considerati uguali se hanno la stessa email.
     * L'email è la chiave naturale: nel dominio della libreria, non possono
     * esistere due utenti registrati con la stessa email.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true; // stesso riferimento in memoria
        if (o == null || getClass() != o.getClass())
            return false; // null o tipo diverso
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    /**
     * hashCode coerente con equals: basato sullo stesso campo (email).
     * Senza questo, inserire User in un HashSet potrebbe creare duplicati.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
