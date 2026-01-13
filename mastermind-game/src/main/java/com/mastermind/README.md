# Master-Mind Game - Application Java

## Description
Application Java du jeu de logique Master-Mind avec interface graphique. Le joueur doit deviner une combinaison secrète de couleurs générée par l'ordinateur.

## Règles du jeu
- L'ordinateur génère une combinaison secrète de 4 couleurs parmi 8 possibles
- Le joueur a 10 tentatives maximum pour deviner la combinaison
- Après chaque proposition, le jeu indique :
  - **#** (noir) : nombre de pions bien placés (bonne couleur, bonne position)
  - **o** (blanc) : nombre de pions mal placés (bonne couleur, mauvaise position)

## Couleurs disponibles
1. Rouge
2. Jaune
3. Vert
4. Bleu
5. Orange
6. Noir
7. Marron
8. Fuchsia

## Structure du projet
```
mastermind/
├── README.md
├── Main.java          # Point d'entrée de l'application
├── MasterMind.java    # Logique du jeu
└── GUI.java           # Interface graphique
```

## Compilation
```bash
javac Main.java MasterMind.java GUI.java
```

## Exécution
```bash
java Main
```

## Fonctionnalités
- Interface graphique intuitive avec boutons de couleurs
- Affichage de l'historique des tentatives
- Indicateurs visuels (# pour bien placé, o pour mal placé)
- Sauvegarde automatique des parties dans des fichiers JSON
- Consultation de l'historique des parties jouées
- Messages de victoire/défaite
- Possibilité de relancer une nouvelle partie

## Sauvegarde des parties
Les parties sont automatiquement sauvegardées dans le dossier `parties/` au format JSON avec :
- Date et heure de la partie
- Combinaison secrète
- Nombre de coups joués
- Statut (gagné/perdu)
- Historique complet des tentatives

## Consultation de l'historique
Cliquez sur le bouton "Historique" pour voir toutes les parties précédemment jouées avec leurs détails.

## Architecture technique
### MasterMind.java
Contient toute la logique du jeu :
- Génération de combinaisons aléatoires
- Vérification des propositions
- Calcul des pions bien/mal placés
- Gestion de la sauvegarde

### GUI.java
Interface graphique Swing avec :
- Sélection des couleurs par boutons
- Affichage de la grille de jeu
- Panneau de résultats
- Boutons d'action

### Main.java
Lance l'application en mode graphique

## Constantes du jeu
- `CMAX = 6` : Taille maximale d'une combinaison
- `n = 4` : Nombre de cases dans la combinaison
- `m = 8` : Nombre de couleurs disponibles
- `maxcoups = 10` : Nombre maximum de tentatives
- `BIEN_PLACE = -1` : Constante interne pour pion bien placé
- `MAL_PLACE = 0` : Constante interne pour pion mal placé

## Exemple de partie
```
Tentative 1: [1, 2, 3, 4] → Résultat: #o
Tentative 2: [1, 3, 2, 5] → Résultat: ##
Tentative 3: [1, 3, 4, 6] → Résultat: ###
Tentative 4: [1, 3, 4, 2] → Résultat: ####
GAGNÉ en 4 coups !
```

## Auteur
Jeu développé en Java avec interface graphique Swing







Utiliser mongodb avec docker

docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin\ 
  -e MONGO_INITDB_ROOT_PASSWORD=1234\ 
  mongo:6.0
















```bash
choco install maven
```

[mongodb-java-driver-docs](https://www.mongodb.com/docs/drivers/java/sync/current/get-started/#install-the-driver-dependencies)

