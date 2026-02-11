# TPs Java - Client-Serveur, Middleware et RMI

Ce repository contient 3 projets Maven correspondant aux exercices 6, 7 et 8 des TPs.

## Vue d'ensemble

### TP6 - Journalisation Client-Serveur
Serveur de données avec opérations arithmétiques et journalisation persistante.
- **Dossier**: `tp6-journalisation/`
- **Ports**: 5000 (serveur)
- **Concepts**: Sockets TCP, persistance de données

### TP7 - Middleware de validation
Architecture à 3 niveaux avec validation de données par middleware.
- **Dossier**: `tp7-middleware/`
- **Ports**: 5001 (middleware), 5002 (serveur)
- **Concepts**: Architecture middleware, validation de données

### TP8 - Validation 2PC et Morpions RMI
Deux exercices avancés: protocole de validation à deux phases et jeu distribué.
- **Dossier**: `tp8-validation-morpions/`
- **Ports**: 
  - Validation 2PC: 5500 (middleware), 6001-6003 (serveurs)
  - Morpions: 1099 (RMI), 7000 (serveur socket)
- **Concepts**: 2PC, Java RMI, objets distants

## Prérequis

- **Java**: JDK 11 ou supérieur
- **Maven**: 3.6 ou supérieur

Vérifier l'installation:
```bash
java -version
mvn -version
```

## Installation rapide

### Cloner et compiler tous les projets
```bash
# Compiler TP6
cd tp6-journalisation
mvn clean install
cd ..

# Compiler TP7
cd tp7-middleware
mvn clean install
cd ..

# Compiler TP8
cd tp8-validation-morpions
mvn clean install
cd ..
```

## Lancement rapide

### TP6 - Journalisation
```bash
# Terminal 1
cd tp6-journalisation
mvn exec:java@run-server

# Terminal 2
cd tp6-journalisation
mvn exec:java@run-client
```

### TP7 - Middleware
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

### TP8 - Validation à deux phases
```bash
# Terminaux 1-3 - Serveurs de données
cd tp8-validation-morpions
mvn exec:java@run-serveur-donnees-1  # Terminal 1
mvn exec:java@run-serveur-donnees-2  # Terminal 2
mvn exec:java@run-serveur-donnees-3  # Terminal 3

# Terminal 4 - Middleware
mvn exec:java@run-middleware-validation

# Terminal 5 - Client
mvn exec:java@run-client-validation
```

### TP8 - Morpions avec RMI
```bash
# Terminal 1 - Serveur
cd tp8-validation-morpions
mvn exec:java@run-serveur-morpions

# Terminal 2 - Joueur 1
mvn exec:java@run-client-morpions

# Terminal 3 - Joueur 2
mvn exec:java@run-client-morpions
```

## Documentation détaillée

Chaque projet contient son propre README avec des instructions détaillées:
- [TP6 README](tp6-journalisation/README.md)
- [TP7 README](tp7-middleware/README.md)
- [TP8 README](tp8-validation-morpions/README.md)

## Structure des projets

```
.
├── README.md (ce fichier)
│
├── tp6-journalisation/
│   ├── pom.xml
│   ├── README.md
│   └── src/main/java/com/tp/tp6/
│       ├── Serveur.java
│       └── Client.java
│
├── tp7-middleware/
│   ├── pom.xml
│   ├── README.md
│   └── src/main/java/com/tp/tp7/
│       ├── Serveur.java
│       ├── Middleware.java
│       └── Client.java
│
└── tp8-validation-morpions/
    ├── pom.xml
    ├── README.md
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

## Ports utilisés

Pour éviter les conflits, voici les ports utilisés par chaque application:

| Projet | Composant | Port |
|--------|-----------|------|
| TP6 | Serveur | 5000 |
| TP7 | Middleware | 5001 |
| TP7 | Serveur | 5002 |
| TP8 - Validation | Middleware | 5500 |
| TP8 - Validation | Serveur 1 | 6001 |
| TP8 - Validation | Serveur 2 | 6002 |
| TP8 - Validation | Serveur 3 | 6003 |
| TP8 - Morpions | RMI Registry | 1099 |
| TP8 - Morpions | Serveur Socket | 7000 |

## Troubleshooting

### Erreur "Address already in use"
Un port est déjà utilisé. Vérifier qu'aucune instance n'est déjà lancée:
```bash
# Linux/Mac
lsof -i :PORT_NUMBER
kill -9 PID

# Windows
netstat -ano | findstr :PORT_NUMBER
taskkill /PID PID /F
```

### Erreur de compilation Maven
```bash
mvn clean compile -U
```

### RMI Registry non disponible (TP8 Morpions)
Le serveur crée automatiquement le registre. Si problème:
```bash
# Lancer manuellement le registre RMI
rmiregistry 1099 &
```

### Problème de connexion client-serveur
Vérifier:
1. Le serveur est bien lancé
2. Les ports correspondent (voir tableau ci-dessus)
3. Pas de firewall bloquant
4. Utiliser `localhost` ou `127.0.0.1`

## Auteur

TPs de programmation réseau en Java - Master Informatique

## Licence

Code pédagogique - Usage académique uniquement
