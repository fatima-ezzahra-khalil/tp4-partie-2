package com.example.jpa;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Author extends Person {

    private String biography;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author() {}

    public Author(String name, String email, String biography) {
        super(name, email);
        this.biography = biography;
    }

    // 🔹 Getters et Setters
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

