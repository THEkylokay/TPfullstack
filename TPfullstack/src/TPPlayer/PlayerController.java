package TPPlayer;

public class PlayerController {
    private PlayerModel model;
    private PlayerView view;

    public PlayerController(PlayerModel model, PlayerView view) {
        this.model = model;
        this.view = view;

        view.addButton.addActionListener(e -> addPlayer());
        view.updateButton.addActionListener(e -> updatePlayer());
        view.deleteButton.addActionListener(e -> deletePlayer());
    }

    private void addPlayer() {
        String nom = view.nomField.getText();
        String prenom = view.prenomField.getText();
        int score = Integer.parseInt(view.scoreField.getText());
        Player p = new Player(nom, prenom, score);
        model.addPlayer(p);
        refreshTable();
    }

    private void updatePlayer() {
        int index = view.table.getSelectedRow();
        if (index >= 0) {
            String nom = view.nomField.getText();
            String prenom = view.prenomField.getText();
            int score = Integer.parseInt(view.scoreField.getText());
            model.updatePlayer(index, new Player(nom, prenom, score));
            refreshTable();
        }
    }

    private void deletePlayer() {
        int index = view.table.getSelectedRow();
        if (index >= 0) {
            model.removePlayer(index);
            refreshTable();
        }
    }

    private void refreshTable() {
        view.tableModel.setRowCount(0);
        for (Player p : model.getPlayers()) {
            view.tableModel.addRow(new Object[]{p.getNom(), p.getPrenom(), p.getScore()});
        }
    }
}
