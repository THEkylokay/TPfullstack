# TP6 - Journalisation en Client-Serveur

## Description
Serveur de données avec journalisation persistante. Le serveur maintient une variable `nb` et accepte des opérations arithmétiques. Toutes les opérations sont enregistrées dans un journal qui permet de restaurer l'état après un redémarrage.

## Fonctionnalités
- Opérations arithmétiques: `+N`, `-N`, `*N`, `/N`
- Affichage de la valeur: `print`
- Journalisation persistante dans `journal.txt`
- Restauration automatique de l'état au démarrage

## Compilation
```bash
cd tp6-journalisation
mvn clean compile
```

## Lancement

### Terminal 1 - Serveur
```bash
mvn exec:java@run-server
```

### Terminal 2 - Client
```bash
mvn exec:java@run-client
```

## Utilisation

Commandes disponibles côté client:
- `+10` : Ajoute 10 à nb
- `-5` : Soustrait 5 de nb
- `*2` : Multiplie nb par 2
- `/3` : Divise nb par 3
- `print` : Affiche la valeur actuelle de nb
- `exit` : Quitte

## Test de la journalisation

1. Lancer le serveur et le client
2. Effectuer quelques opérations (ex: `+5`, `*3`, `print`)
3. Taper `exit` pour quitter le client
4. Arrêter le serveur (Ctrl+C)
5. Relancer le serveur
6. Le serveur affichera la valeur restaurée depuis le journal
7. Lancer un nouveau client et taper `print` pour vérifier

Le fichier `journal.txt` contient toutes les opérations effectuées.
