# TP7 - Espace de travail privé avec Middleware

## Description
Système client-middleware-serveur permettant de construire des phrases en minuscules. Le middleware valide que les mots envoyés ne contiennent que des lettres avant de les transmettre au serveur.

## Architecture
- **Client** (port 5001) → **Middleware** (port 5001) → **Serveur** (port 5002)
- Le middleware vérifie la conformité des mots (lettres uniquement)
- Le serveur stocke les mots en minuscules et les concatène

## Fonctionnalités
- Validation des mots (uniquement des lettres, pas de chiffres/ponctuation/espaces)
- Conversion automatique en minuscules
- Commande `print` pour afficher la phrase complète
- Réinitialisation automatique après affichage

## Compilation
```bash
cd tp7-middleware
mvn clean compile
```

## Lancement

### Terminal 1 - Serveur
```bash
mvn exec:java@run-server
```

### Terminal 2 - Middleware
```bash
mvn exec:java@run-middleware
```

### Terminal 3 - Client
```bash
mvn exec:java@run-client
```

## Utilisation

Commandes disponibles côté client:
- `Bonjour` : Ajoute "bonjour" (accepté)
- `Hello123` : Rejeté (contient des chiffres)
- `mot!` : Rejeté (contient de la ponctuation)
- `print` : Affiche la phrase et la réinitialise
- `exit` : Quitte

## Exemple d'utilisation

```
Client> Bonjour
Réponse: OK: Mot ajouté

Client> le
Réponse: OK: Mot ajouté

Client> MONDE
Réponse: OK: Mot ajouté

Client> print
Réponse: PHRASE: bonjour le monde

Client> test123
Réponse: ERREUR: Le mot contient des caractères non autorisés
```

## Points importants
- Le middleware filtre les mots AVANT de les envoyer au serveur
- Les majuscules sont automatiquement converties en minuscules par le serveur
- La phrase est réinitialisée après chaque `print`
