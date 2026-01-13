package TPSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GestionBibliotheque {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BibliothequeFrame();
        });
    }
}

// Classe représentant un livre
class Livre {
    private String titre;
    private String auteur;
    private int annee;
    private String genre;

    public Livre(String titre, String auteur, int annee, String genre) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.genre = genre;
    }

    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getAnnee() { return annee; }
    public String getGenre() { return genre; }

    public void setTitre(String titre) { this.titre = titre; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public void setAnnee(int annee) { this.annee = annee; }
    public void setGenre(String genre) { this.genre = genre; }
}

// Classe principale pour la fenêtre
class BibliothequeFrame extends JFrame {
    private List<Livre> livres = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;

    public BibliothequeFrame() {
        setTitle("Gestion de Bibliothèque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Création du panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrerLivres();
            }
        });
        searchPanel.add(new JLabel("Rechercher: "));
        searchPanel.add(searchField);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton sortButton = new JButton("Trier par...");

        addButton.addActionListener(e -> ajouterLivre());
        editButton.addActionListener(e -> modifierLivre());
        deleteButton.addActionListener(e -> supprimerLivre());
        sortButton.addActionListener(e -> trierLivres());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);

        // Tableau des livres
        String[] columns = {"Titre", "Auteur", "Année", "Genre"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Ajout des composants au panneau principal
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void ajouterLivre() {
        JTextField titreField = new JTextField();
        JTextField auteurField = new JTextField();
        JTextField anneeField = new JTextField();
        JTextField genreField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Titre:"));
        panel.add(titreField);
        panel.add(new JLabel("Auteur:"));
        panel.add(auteurField);
        panel.add(new JLabel("Année:"));
        panel.add(anneeField);
        panel.add(new JLabel("Genre:"));
        panel.add(genreField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Ajouter un livre", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titre = titreField.getText().trim();
                String auteur = auteurField.getText().trim();
                String anneeText = anneeField.getText().trim();
                String genre = genreField.getText().trim();

                if (titre.isEmpty() || auteur.isEmpty() || anneeText.isEmpty() || genre.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Tous les champs doivent être remplis",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int annee = Integer.parseInt(anneeText);
                Livre livre = new Livre(titre, auteur, annee, genre);
                livres.add(livre);
                updateTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "L'année doit être un nombre valide",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierLivre() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un livre à modifier",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livre livre = livres.get(selectedRow);
        JTextField titreField = new JTextField(livre.getTitre());
        JTextField auteurField = new JTextField(livre.getAuteur());
        JTextField anneeField = new JTextField(String.valueOf(livre.getAnnee()));
        JTextField genreField = new JTextField(livre.getGenre());

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Titre:"));
        panel.add(titreField);
        panel.add(new JLabel("Auteur:"));
        panel.add(auteurField);
        panel.add(new JLabel("Année:"));
        panel.add(anneeField);
        panel.add(new JLabel("Genre:"));
        panel.add(genreField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Modifier le livre", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titre = titreField.getText().trim();
                String auteur = auteurField.getText().trim();
                String anneeText = anneeField.getText().trim();
                String genre = genreField.getText().trim();

                if (titre.isEmpty() || auteur.isEmpty() || anneeText.isEmpty() || genre.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Tous les champs doivent être remplis",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int annee = Integer.parseInt(anneeText);
                livre.setTitre(titre);
                livre.setAuteur(auteur);
                livre.setAnnee(annee);
                livre.setGenre(genre);
                updateTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "L'année doit être un nombre valide",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerLivre() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un livre à supprimer",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer ce livre ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            livres.remove(selectedRow);
            updateTable();
        }
    }

    private void filtrerLivres() {
        String searchText = searchField.getText().toLowerCase().trim();
        tableModel.setRowCount(0);

        for (Livre livre : livres) {
            if (livre.getTitre().toLowerCase().contains(searchText) ||
                    livre.getAuteur().toLowerCase().contains(searchText)) {
                tableModel.addRow(new Object[]{
                        livre.getTitre(),
                        livre.getAuteur(),
                        livre.getAnnee(),
                        livre.getGenre()
                });
            }
        }
    }

    private void trierLivres() {
        String[] options = {"Titre", "Auteur", "Année"};
        String choice = (String) JOptionPane.showInputDialog(this,
                "Trier par:", "Tri des livres",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice != null) {
            switch (choice) {
                case "Titre":
                    livres.sort(Comparator.comparing(Livre::getTitre));
                    break;
                case "Auteur":
                    livres.sort(Comparator.comparing(Livre::getAuteur));
                    break;
                case "Année":
                    livres.sort(Comparator.comparingInt(Livre::getAnnee));
                    break;
            }
            updateTable();
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Livre livre : livres) {
            tableModel.addRow(new Object[]{
                    livre.getTitre(),
                    livre.getAuteur(),
                    livre.getAnnee(),
                    livre.getGenre()
            });
        }
    }
}