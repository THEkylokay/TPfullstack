#!/bin/bash

echo "========================================="
echo "TP7 - Middleware de validation"
echo "========================================="
echo ""
echo "Choisissez une option:"
echo "1) Lancer le serveur"
echo "2) Lancer le middleware"
echo "3) Lancer le client"
echo "4) Compiler le projet"
echo "5) Quitter"
echo ""
read -p "Votre choix: " choice

case $choice in
    1)
        echo "Lancement du serveur (port 5002)..."
        mvn exec:java@run-server
        ;;
    2)
        echo "Lancement du middleware (port 5001)..."
        mvn exec:java@run-middleware
        ;;
    3)
        echo "Lancement du client..."
        mvn exec:java@run-client
        ;;
    4)
        echo "Compilation..."
        mvn clean compile
        ;;
    5)
        echo "Au revoir!"
        exit 0
        ;;
    *)
        echo "Choix invalide"
        exit 1
        ;;
esac
