# Guide de Démarrage Rapide - TPs Java

## Installation

1. **Prérequis**
   - Java JDK 11+ installé
   - Maven 3.6+ installé
   
   Vérifier:
   ```bash
   java -version
   mvn -version
   ```

2. **Extraire les fichiers**
   - Décompresser l'archive dans un dossier de votre choix

## Démarrage Ultra-Rapide

### Méthode 1: Scripts Shell (Linux/Mac)

Chaque projet contient un script `run.sh` :

```bash
cd tp6-journalisation
./run.sh
# Choisir l'option 1 pour le serveur, puis dans un autre terminal:
./run.sh
# Choisir l'option 2 pour le client
```

### Méthode 2: Commandes Maven Directes

**TP6 - Journalisation:**
```bash
# Terminal 1 - Serveur
cd tp6-journalisation
mvn exec:java@run-server

# Terminal 2 - Client
cd tp6-journalisation
mvn exec:java@run-client
```

**TP7 - Middleware:**
```bash
# Terminal 1 - Serveur
cd tp7-middleware
mvn exec:java@run-server

# Terminal 2 - Middleware
cd tp7-middleware
mvn exec:java@run-middleware

# Terminal 3 - Client
cd tp7-middleware
mvn exec:java@run-client
```

**TP8 - Validation 2PC:**
```bash
# Terminaux 1-3 - Serveurs
cd tp8-validation-morpions
mvn exec:java@run-serveur-donnees-1
mvn exec:java@run-serveur-donnees-2
mvn exec:java@run-serveur-donnees-3

# Terminal 4 - Middleware
mvn exec:java@run-middleware-validation

# Terminal 5 - Client
mvn exec:java@run-client-validation
```

**TP8 - Morpions RMI:**
```bash
# Terminal 1 - Serveur
cd tp8-validation-morpions
mvn exec:java@run-serveur-morpions

# Terminaux 2-3 - Joueurs
mvn exec:java@run-client-morpions
mvn exec:java@run-client-morpions
```

## Ordre de Lancement

**IMPORTANT:** Toujours lancer les composants dans cet ordre:

### TP6
1. Serveur
2. Client(s)

### TP7
1. Serveur
2. Middleware
3. Client(s)

### TP8 - Validation
1. Les 3 serveurs de données (dans n'importe quel ordre)
2. Middleware
3. Client(s)

### TP8 - Morpions
1. Serveur
2. Client joueur 1
3. Client joueur 2

## Tests Rapides

### TP6
```
Client> +10
Client> *3
Client> print
Client> exit
# Redémarrer le serveur et un client
Client> print
# Vérifie que la valeur est restaurée
```

### TP7
```
Client> Bonjour
Client> le
Client> MONDE
Client> print
Client> test123
# Erreur attendue
```

### TP8 - Validation
```
Client> +5
Client> print
# Affiche les 3 serveurs
Client> *10
# Peut échouer (simulation de panne)
```

### TP8 - Morpions
```
Joueur 1> 1  (ligne)
Joueur 1> 1  (colonne)
# Joueur 2 joue à son tour
# Continuer jusqu'à victoire ou match nul
```

## Problèmes Courants

### "Port already in use"
Un processus utilise déjà le port. Tuer le processus:
```bash
# Linux/Mac
lsof -i :PORT
kill -9 PID

# Windows
netstat -ano | findstr :PORT
taskkill /PID PID /F
```

### "Connection refused"
Le serveur n'est pas lancé. Vérifier:
1. Le serveur est bien démarré
2. L'ordre de lancement est respecté
3. Aucune erreur dans le terminal du serveur

### Compilation échoue
```bash
mvn clean install -U
```

### RMI ne fonctionne pas
Vérifier que le registre RMI est créé (automatique avec le serveur de morpions)

## Structure des Projets

```
.
├── README.md                          # Ce fichier
├── QUICK_START.md                     # Guide de démarrage rapide
├── tp6-journalisation/
│   ├── pom.xml                        # Configuration Maven
│   ├── README.md                      # Documentation TP6
│   ├── run.sh                         # Script de lancement
│   └── src/main/java/com/tp/tp6/
│       ├── Serveur.java
│       └── Client.java
├── tp7-middleware/
│   ├── pom.xml
│   ├── README.md
│   ├── run.sh
│   └── src/main/java/com/tp/tp7/
│       ├── Serveur.java
│       ├── Middleware.java
│       └── Client.java
└── tp8-validation-morpions/
    ├── pom.xml
    ├── README.md
    ├── run.sh
    └── src/main/java/com/tp/tp8/
        ├── validation/
        │   ├── ServeurDonnees.java
        │   ├── MiddlewareValidation.java
        │   └── ClientValidation.java
        └── morpions/
            ├── JeuMorpions.java
            ├── JeuMorpionsImpl.java
            ├── ServeurMorpions.java
            └── ClientMorpions.java
```

## Ports Utilisés

| Projet | Composant | Port |
|--------|-----------|------|
| TP6 | Serveur | 5000 |
| TP7 | Middleware | 5001 |
| TP7 | Serveur | 5002 |
| TP8-Validation | Middleware | 5500 |
| TP8-Validation | Serveur 1-3 | 6001-6003 |
| TP8-Morpions | RMI | 1099 |
| TP8-Morpions | Socket | 7000 |

## Ressources

- **Documentation complète:** Voir les README.md dans chaque projet
- **Code source:** Tous les fichiers .java sont commentés
- **Exemples d'utilisation:** Inclus dans les README

## Support

Pour toute question sur les TPs, consulter:
1. Le README.md spécifique du projet
2. Le code source (commenté)
3. La documentation Maven (pom.xml)

Bon courage avec les TPs ! 🚀
