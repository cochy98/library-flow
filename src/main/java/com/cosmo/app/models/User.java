package com.cosmo.app.models;

import java.util.Objects;

public class User extends Item {
    private String name;
    private String surname;
    private String email;

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Overrides
    @Override
    public String toString() {
        return "Nome completo: " + this.name + " " + this.surname + "\n" +
                "Email: " + this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true; // stesso riferimento
        if (o == null || getClass() != o.getClass())
            return false; // null o classe diversa
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
