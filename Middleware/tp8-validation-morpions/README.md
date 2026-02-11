# TP8 - Validation à deux phases et Morpions avec RMI

Ce projet contient deux exercices distincts:
1. **Validation à deux phases (2PC)** - Exercice 8
2. **Jeu de Morpions avec RMI** - Exercice 9

## Compilation globale
```bash
cd tp8-validation-morpions
mvn clean compile
```

---

## Exercice 8 - Validation à deux phases (2PC)

### Description
Système de validation à deux phases avec 3 serveurs de données identiques. Le middleware coordonne les transactions avec un protocole 2PC (Two-Phase Commit).

### Architecture
- **Client** → **Middleware** (port 5500) → **3 Serveurs de données** (ports 6001, 6002, 6003)
- Chaque serveur a son propre journal de persistence
- Simulation de pannes: 90% de disponibilité (phase PREPARE), 95% de réussite d'écriture (phase COMMIT)

### Protocole 2PC
1. **Phase PREPARE**: Le middleware demande à tous les serveurs s'ils sont prêts
   - Si un serveur répond NON → Transaction annulée
2. **Phase COMMIT**: Si tous sont prêts, le middleware demande d'appliquer
   - Si un serveur échoue → ROLLBACK sur tous les serveurs

### Lancement

#### Terminal 1 - Serveur de données 1
```bash
mvn exec:java@run-serveur-donnees-1
```

#### Terminal 2 - Serveur de données 2
```bash
mvn exec:java@run-serveur-donnees-2
```

#### Terminal 3 - Serveur de données 3
```bash
mvn exec:java@run-serveur-donnees-3
```

#### Terminal 4 - Middleware
```bash
mvn exec:java@run-middleware-validation
```

#### Terminal 5 - Client
```bash
mvn exec:java@run-client-validation
```

### Utilisation

Commandes disponibles:
- `+10`, `-5`, `*2`, `/3` : Opérations (soumises au protocole 2PC)
- `print` : Afficher les valeurs des 3 serveurs
- `exit` : Quitter

### Exemple de scénario

```
Client> +5
Réponse du middleware:
  OK: Opération validée et appliquée sur tous les serveurs

Client> print
Réponse du middleware:
  Serveur 1: nb=6
  Serveur 2: nb=6
  Serveur 3: nb=6

Client> *10
Réponse du middleware:
  ERREUR: Le serveur 2 n'est pas disponible

Client> /2
Réponse du middleware:
  ERREUR: Le serveur 3 n'a pas pu valider l'opération. Rollback effectué.
```

### Journaux persistants
Chaque serveur crée son propre journal:
- `journal_serveur_1.txt`
- `journal_serveur_2.txt`
- `journal_serveur_3.txt`

Ces journaux permettent de restaurer l'état après redémarrage.

---

## Exercice 9 - Jeu de Morpions avec RMI

### Description
Jeu de morpions (tic-tac-toe) pour 2 joueurs utilisant:
- **Sockets TCP** pour la communication client-serveur
- **Java RMI** pour accéder à l'objet distant du jeu

### Fonctionnalités
- Grille 3x3
- Détection automatique de victoire (lignes, colonnes, diagonales)
- Détection de match nul
- Gestion du tour par tour
- Possibilité de rejouer

### Lancement

#### Terminal 1 - Serveur
```bash
mvn exec:java@run-serveur-morpions
```

Le serveur:
- Crée le registre RMI sur le port 1099
- Enregistre l'objet distant `JeuMorpions`
- Écoute les connexions socket sur le port 7000
- Accepte exactement 2 joueurs

#### Terminal 2 - Client Joueur 1
```bash
mvn exec:java@run-client-morpions
```

#### Terminal 3 - Client Joueur 2
```bash
mvn exec:java@run-client-morpions
```

### Utilisation

Une fois les 2 joueurs connectés:

1. La grille s'affiche automatiquement
2. Le joueur dont c'est le tour entre ses coordonnées:
   - Ligne (0-2)
   - Colonne (0-2)
3. La grille se met à jour
4. Le jeu détecte automatiquement:
   - La victoire d'un joueur
   - Le match nul
5. Possibilité de rejouer à la fin

### Exemple de partie

```
    0   1   2
  +---+---+---+
0 |   |   |   |
  +---+---+---+
1 |   |   |   |
  +---+---+---+
2 |   |   |   |
  +---+---+---+

>>> C'est votre tour! <<<
Entrez la ligne (0-2) ou 'q' pour quitter: 1
Entrez la colonne (0-2): 1
Coup joué!

    0   1   2
  +---+---+---+
0 |   |   |   |
  +---+---+---+
1 |   | X |   |
  +---+---+---+
2 |   |   |   |
  +---+---+---+

>>> C'est le tour du joueur 2 <<<
En attente...
```

### Architecture technique

- **Communication Socket**: Échange de messages entre client et serveur
  - `JOUEUR:N` : Assignation du numéro de joueur
  - `RMI_URL:...` : URL de l'objet distant
  - `JOUER:ligne:colonne` : Jouer un coup
  - `GRILLE` : Demander la grille
  
- **RMI (Remote Method Invocation)**:
  - `obtenirGrille()` : Récupère l'état de la grille
  - `jouerCoup(joueur, ligne, colonne)` : Place un symbole
  - `verifierVictoire()` : Vérifie s'il y a un gagnant
  - `verifierMatchNul()` : Vérifie le match nul
  - `obtenirJoueurActuel()` : Obtient le joueur dont c'est le tour
  - `reinitialiser()` : Recommence une partie

### Points importants

- Le serveur gère le tour par tour
- Les clients utilisent RMI pour accéder directement à l'état du jeu
- La synchronisation est gérée par l'objet distant (méthodes `synchronized`)
- Maximum 2 joueurs simultanés
- Si un joueur se déconnecte, la partie se réinitialise quand tous sont partis

---

## Notes de développement

### Structure du projet
```
tp8-validation-morpions/
├── pom.xml
├── README.md
└── src/main/java/com/tp/tp8/
    ├── validation/
    │   ├── ServeurDonnees.java
    │   ├── MiddlewareValidation.java
    │   └── ClientValidation.java
    └── morpions/
        ├── JeuMorpions.java (interface RMI)
        ├── JeuMorpionsImpl.java (implémentation)
        ├── ServeurMorpions.java
        └── ClientMorpions.java
```

### Dépendances
- Java 11 ou supérieur
- Maven 3.6+
- Aucune dépendance externe (utilise uniquement l'API standard Java)
