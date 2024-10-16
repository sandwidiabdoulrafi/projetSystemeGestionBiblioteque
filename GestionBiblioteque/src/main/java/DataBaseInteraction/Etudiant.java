/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseInteraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;

/**
 *
 * @author User
 */
public class Etudiant {
    // Attributs de la classe
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private Connection connection;

    // Constructeur
    public Etudiant(){}
    public Etudiant(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        
        connection = DatabaseConnection.getConnection();
        String requete = "INSERT INTO Etudiants (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, motDePasse);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Compte créé avec succès !");
            } else {
                System.out.println("Échec de la création du compte.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public Etudiant(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getters et Setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    // Méthodes pour gérer les fonctionnalités de l'étudiant
    public void voirLivresDisponibles() {
        String requete = "SELECT id, titre, auteur, maison_edition FROM Livres WHERE dateEmprunt IS NULL";

        try (PreparedStatement preparedStatement = connection.prepareStatement(requete);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Livres disponibles :");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                String maisonEdition = resultSet.getString("maison_edition");

                System.out.println("ID: " + id + ", Titre: " + titre + ", Auteur: " + auteur + ", Maison d'édition: " + maisonEdition);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean emprunterLivre(int etudiantId, int livreId) {
        String requete = "UPDATE Livres SET etudiant_id = ?, bibliothecaire_id = NULL, dateEmprunt = CURRENT_DATE, dateRetourPrevue = CURRENT_DATE + INTERVAL '3 weeks', statut = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1, etudiantId);
            preparedStatement.setString(2, "Indisponible");
            preparedStatement.setInt(3, livreId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Livre emprunté avec succès.");
                return true;
            } else {
                System.out.println("Aucun livre trouvé avec cet ID.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void retournerLivre(int livreId) {
        String requete = "UPDATE Livres SET etudiant_id = NULL, dateEmprunt = NULL, dateRetourPrevue = NULL, statut = 'Disponible' WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1, livreId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Livre retourné avec succès.");
            } else {
                System.out.println("Aucun livre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void payerSomme(double montant) {
        System.out.println("Paiement de " + montant + " euros effectué avec succès.");
    }

    public boolean seConnecter() {
        Connection connection = DatabaseConnection.getConnection();
        String requete = "SELECT * FROM Etudiants WHERE email = ? AND motDePasse = ?";
        boolean connexionReussie = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, this.email);
            preparedStatement.setString(2, this.motDePasse);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                connexionReussie = true;
                System.out.println("Connexion réussie !");
                this.id = resultSet.getInt("id");
                this.nom = resultSet.getString("nom");
                this.prenom = resultSet.getString("prenom");
                this.email = resultSet.getString("email");
            } else {
                System.out.println("Échec de la connexion : email ou mot de passe incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return connexionReussie;
    }

    public boolean creerCompte(String nom, String prenom, String email, String motDePasse) {
        Connection connection = DatabaseConnection.getConnection();
        String requete = "INSERT INTO Etudiants (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
        boolean isInscrit = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, motDePasse);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Compte créé avec succès !");
                isInscrit = true;
                return isInscrit;
            } else {
                System.out.println("Échec de la création du compte.");
                isInscrit = false;
                return isInscrit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
