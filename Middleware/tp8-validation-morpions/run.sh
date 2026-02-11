#!/bin/bash

echo "========================================="
echo "TP8 - Validation 2PC et Morpions RMI"
echo "========================================="
echo ""
echo "Choisissez un exercice:"
echo ""
echo "EXERCICE 8 - Validation à deux phases"
echo "1) Lancer le serveur de données 1 (port 6001)"
echo "2) Lancer le serveur de données 2 (port 6002)"
echo "3) Lancer le serveur de données 3 (port 6003)"
echo "4) Lancer le middleware (port 5500)"
echo "5) Lancer le client"
echo ""
echo "EXERCICE 9 - Morpions avec RMI"
echo "6) Lancer le serveur de morpions (ports 1099/7000)"
echo "7) Lancer un client joueur"
echo ""
echo "AUTRES"
echo "8) Compiler le projet"
echo "9) Quitter"
echo ""
read -p "Votre choix: " choice

case $choice in
    1)
        echo "Lancement du serveur de données 1..."
        mvn exec:java@run-serveur-donnees-1
        ;;
    2)
        echo "Lancement du serveur de données 2..."
        mvn exec:java@run-serveur-donnees-2
        ;;
    3)
        echo "Lancement du serveur de données 3..."
        mvn exec:java@run-serveur-donnees-3
        ;;
    4)
        echo "Lancement du middleware de validation..."
        mvn exec:java@run-middleware-validation
        ;;
    5)
        echo "Lancement du client de validation..."
        mvn exec:java@run-client-validation
        ;;
    6)
        echo "Lancement du serveur de morpions..."
        mvn exec:java@run-serveur-morpions
        ;;
    7)
        echo "Lancement d'un client joueur..."
        mvn exec:java@run-client-morpions
        ;;
    8)
        echo "Compilation..."
        mvn clean compile
        ;;
    9)
        echo "Au revoir!"
        exit 0
        ;;
    *)
        echo "Choix invalide"
        exit 1
        ;;
esac
