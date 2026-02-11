#!/bin/bash

echo "========================================="
echo "TP6 - Journalisation Client-Serveur"
echo "========================================="
echo ""
echo "Choisissez une option:"
echo "1) Lancer le serveur"
echo "2) Lancer le client"
echo "3) Compiler le projet"
echo "4) Quitter"
echo ""
read -p "Votre choix: " choice

case $choice in
    1)
        echo "Lancement du serveur..."
        mvn exec:java@run-server
        ;;
    2)
        echo "Lancement du client..."
        mvn exec:java@run-client
        ;;
    3)
        echo "Compilation..."
        mvn clean compile
        ;;
    4)
        echo "Au revoir!"
        exit 0
        ;;
    *)
        echo "Choix invalide"
        exit 1
        ;;
esac
