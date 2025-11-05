package com.example.jpa;

import jakarta.persistence.*;
import java.util.List;

public class MainPartie2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp4PU");
        EntityManager em = emf.createEntityManager();

        System.out.println("\n===== 🔹 PARTIE 2 : Requêtes et manipulations 🔹 =====");

        // Afficher tous les livres d'une catégorie donnée
        System.out.println("\n---  Livres d'une catégorie ---");
        TypedQuery<Book> q1 = em.createQuery(
                "SELECT b FROM Book b JOIN b.categories c WHERE c.name = :nomCategorie", Book.class);
        q1.setParameter("nomCategorie", "Programming");

        List<Book> livresCategorie = q1.getResultList();
        for (Book b : livresCategorie) {
            System.out.println("📘 Livre : " + b.getTitle() + " | Catégorie : Programming");
        }

        // Livres publiés par un éditeur spécifique
        System.out.println("\n---  Livres d'un éditeur ---");
        TypedQuery<Book> q2 = em.createQuery(
                "SELECT b FROM Book b WHERE b.publisher.name = :nomEditeur", Book.class);
        q2.setParameter("nomEditeur", "O'Reilly Media");

        List<Book> livresEditeur = q2.getResultList();
        for (Book b : livresEditeur) {
            System.out.println("📚 Livre publié par O'Reilly Media : " + b.getTitle());
        }

        //Réinsertion d’un livre pour les tests
        em.getTransaction().begin();
        Publisher pub = new Publisher();
        pub.setName("O'Reilly Media");
        pub.setCountry("USA");
        em.persist(pub);

        Book nouveauLivre = new Book();
        nouveauLivre.setTitle("Learning JPA");
        nouveauLivre.setAuthor("John Doe");
        nouveauLivre.setPrice(100);
        nouveauLivre.setDiscountedPrice(80);
        nouveauLivre.setPublisher(pub);
        em.persist(nouveauLivre);
        em.getTransaction().commit();

        int newBookId = nouveauLivre.getId(); // ✅ on récupère l'ID généré automatiquement

        //  Mise à jour du prix (avant suppression)
        System.out.println("\n---  Mise à jour du prix ---");
        em.getTransaction().begin();
        Book livreAModifier = em.find(Book.class, newBookId);
        if (livreAModifier != null) {
            livreAModifier.setPrice(60);
            livreAModifier.setDiscountedPrice(50);
            System.out.println("💰 Prix mis à jour pour le livre : " + livreAModifier.getTitle());
        } else {
            System.out.println("⚠️ Livre introuvable !");
        }
        em.getTransaction().commit();

        // Suppression du livre et effet du CascadeType
        System.out.println("\n---  Suppression d'un livre ---");
        em.getTransaction().begin();
        Book livreASupprimer = em.find(Book.class, newBookId);
        if (livreASupprimer != null) {
            em.remove(livreASupprimer);
            System.out.println("🗑️ Livre supprimé : " + livreASupprimer.getTitle());
        } else {
            System.out.println("⚠️ Livre introuvable !");
        }
        em.getTransaction().commit();

        // Test du chargement EAGER vs LAZY
        System.out.println("\n--- Test EAGER vs LAZY ---");
        em.getTransaction().begin();
        Book livre = em.find(Book.class, newBookId);
        em.getTransaction().commit();
        em.close();

        try {
            System.out.println("Nombre d'avis : " + livre.getReviews().size());
        } catch (Exception e) {
            System.out.println("⚠️ LazyInitializationException : les reviews ne sont pas chargées !");
        }

        emf.close();
    }
}
