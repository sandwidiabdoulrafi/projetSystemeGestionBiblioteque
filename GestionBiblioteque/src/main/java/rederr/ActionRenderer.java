/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rederr;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ActionRenderer extends DefaultTableCellRenderer {

    public ActionRenderer(JPanel panel) {
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        JButton button = new JButton("Action"); // Créez un bouton avec un texte
        button.setPreferredSize(new Dimension(80, 30)); // Définissez une taille préférée pour le bouton

        // Personnalisez l'apparence du bouton si nécessaire
        if (isSelected) {
            button.setBackground(Color.BLUE); // Couleur de fond si la cellule est sélectionnée
            button.setForeground(Color.WHITE); // Couleur du texte si la cellule est sélectionnée
        } else {
            button.setBackground(Color.LIGHT_GRAY); // Couleur de fond si la cellule n'est pas sélectionnée
            button.setForeground(Color.BLACK); // Couleur du texte si la cellule n'est pas sélectionnée
        }

        return button; // Retournez le bouton comme le composant à afficher dans la cellule
    }
}
