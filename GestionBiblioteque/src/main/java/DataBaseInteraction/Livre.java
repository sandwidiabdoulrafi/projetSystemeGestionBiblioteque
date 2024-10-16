/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseInteraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DataBaseInteraction.DatabaseConnection;
import static DataBaseInteraction.DatabaseConnection.connect;
import java.sql.ResultSet;
/**

/**
 *
 * @author User
 */
import java.time.LocalDate;

public class Livre {
    private int Id;
    private String titre;
    private String auteur;
    private String maison_edition ;
    private String status = "Disponible";
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private Connection connection ;

    // Constructeur
    public Livre(String titre, String auteur,String maison_edition) {
        this.maison_edition =maison_edition;
        this.titre = titre;
        this.auteur = auteur;
        this.dateEmprunt = null; // Initialisé à null jusqu'à ce qu'un livre soit emprunté
        this.dateRetourPrevue = null; // Initialisé à null jusqu'à ce qu'un livre soit emprunté
        
         this.connection = DatabaseConnection.getConnection();
    }
     public boolean enregistrerLivre() {
        String requete = "INSERT INTO Livres (titre, auteur, maison_edition, dateEmprunt, dateRetourPrevue,status) VALUES (?, ?, ?, ?, ?,status)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, this.titre);
            preparedStatement.setString(2, this.auteur);
            preparedStatement.setString(3, this.maison_edition);
            preparedStatement.setObject(4, this.dateEmprunt); 
            preparedStatement.setObject(5, this.dateRetourPrevue);
            preparedStatement.setObject(5, this.status);

            int rowsAffected = preparedStatement.executeUpdate(); 

            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean supprimerLivreParId() {
        String requete = "DELETE FROM Livres WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1, this.Id); // Définit l'ID du livre à supprimer

            int lignesAffectees = preparedStatement.executeUpdate(); // Exécute la requête

            // Si au moins une ligne est affectée, la suppression a réussi
            return lignesAffectees > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean emprunterLivre(int etudiantId) {
        String requete = "UPDATE Livres SET etudiant_id = ?, bibliothecaire_id = NULL, dateEmprunt = CURRENT_DATE, dateRetourPrevue = CURRENT_DATE + INTERVAL '3 weeks' WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setInt(1, etudiantId);
            preparedStatement.setInt(2, this.Id);
            preparedStatement.setObject(5, "Indisponible");

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Livre emprunté par l'étudiant avec succès.");
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
    
    
    
    

    // Getters et Setters
    public String getTitre() {
        return titre;
    }
    public String getMaisonEdition() {
        return maison_edition ;
    }
    public String getStatus() {
        return status ;
    }


    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
     public void setMaison_edition(String maison_edition) {
        this.maison_edition = maison_edition;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }
    

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }
    
    
}
