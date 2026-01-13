package TPPlayer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlayerView extends JFrame {
    public JTextField nomField = new JTextField(10);
    public JTextField prenomField = new JTextField(10);
    public JTextField scoreField = new JTextField(5);

    public JButton addButton = new JButton("Ajouter");
    public JButton updateButton = new JButton("Modifier");
    public JButton deleteButton = new JButton("Supprimer");

    public DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Nom", "Prénom", "Score"}, 0);
    public JTable table = new JTable(tableModel);

    public PlayerView() {
        setTitle("Gestion des Joueurs");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Nom:"));
        inputPanel.add(nomField);
        inputPanel.add(new JLabel("Prénom:"));
        inputPanel.add(prenomField);
        inputPanel.add(new JLabel("Score:"));
        inputPanel.add(scoreField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }
}
