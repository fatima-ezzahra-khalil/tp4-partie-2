package com.example.jpa;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp4PU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Créer un éditeur
        Publisher publisher = new Publisher();
        publisher.setName("O'Reilly Media");
        publisher.setCountry("USA");

        // Créer des catégories
        Category cat1 = new Category();
        cat1.setName("Programming");

        Category cat2 = new Category();
        cat2.setName("Databases");

        // Créer un livre
        Book book = new Book();
        book.setTitle("Learning JPA");
        book.setAuthor("John Doe");
        book.setPrice(50);
        book.setDiscountedPrice(45);
        book.setPublisher(publisher);
        book.setCategories(Arrays.asList(cat1, cat2));

        // Créer plusieurs avis
        Review r1 = new Review();
        r1.setComment("Excellent livre !");
        r1.setBook(book);

        Review r2 = new Review();
        r2.setComment("Très utile pour les débutants.");
        r2.setBook(book);

        book.setReviews(Arrays.asList(r1, r2));
        publisher.setBooks(List.of(book));

        // Persister les objets
        em.persist(publisher);
        em.persist(cat1);
        em.persist(cat2);
        em.persist(book);

        em.getTransaction().commit();

        // Lire et afficher les livres
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        for (Book b : books) {
            System.out.println("\n📚 Livre : " + b.getTitle());
            System.out.println("Auteur : " + b.getAuthor());
            System.out.println("Éditeur : " + b.getPublisher().getName());
            System.out.println("Catégories : ");
            for (Category c : b.getCategories()) {
                System.out.println(" - " + c.getName());
            }
            System.out.println("Avis : ");
            for (Review r : b.getReviews()) {
                System.out.println("   * " + r.getComment());
            }
        }

        em.close();
        emf.close();
    }
}
