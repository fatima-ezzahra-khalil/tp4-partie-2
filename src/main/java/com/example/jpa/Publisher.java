package com.example.jpa;

import com.example.jpa.Person;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Publisher extends Person {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    private Long id;
    private String companyName;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Book> books;

    public Publisher() {}

    public Publisher(String name, String email, String companyName) {
        super(name, email);
        this.companyName = companyName;
    }

    // getters et setters
}
