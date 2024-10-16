
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseInteraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BibliothecaireRole {
    private Connection connection;

    public BibliothecaireRole(Connection connection) {
        this.connection = connection;
    }
    public BibliothecaireRole() {
        
    }

    public boolean seConnecter(String email, String motDePasse) {
        String query = "SELECT * FROM Bibliothecaires WHERE email = ? AND motDePasse = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, motDePasse);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Connexion réussie si l'utilisateur existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inscrire(String nom, String prenom, String email, String motDePasse) {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            statement.setString(4, motDePasse);
            statement.executeUpdate();
            return true; // Inscription réussie
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Erreur lors de l'inscription
        }
    }

    public List<String> voirLivresDisponibles() {
        List<String> livres = new ArrayList<>();
        String query = "SELECT titre FROM livres WHERE disponible = TRUE";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                livres.add(resultSet.getString("titre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
}
