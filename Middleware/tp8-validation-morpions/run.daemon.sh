#!/bin/bash

# Couleurs pour l'affichage
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

PID_DIR="./pids"
LOG_DIR="./logs"

# Créer les dossiers nécessaires
mkdir -p "$PID_DIR" "$LOG_DIR"

# Fonction pour démarrer un processus en daemon
start_daemon() {
    local name=$1
    local exec_id=$2
    local args=$3
    local pid_file="$PID_DIR/${name}.pid"
    local log_file="$LOG_DIR/${name}.log"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if ps -p $pid > /dev/null 2>&1; then
            echo -e "${YELLOW}[!] $name est déjà en cours d'exécution (PID: $pid)${NC}"
            return 1
        fi
    fi
    
    echo -e "${BLUE}[*] Démarrage de $name...${NC}"
    nohup mvn exec:java@${exec_id} ${args} > "$log_file" 2>&1 &
    local pid=$!
    echo $pid > "$pid_file"
    
    # Attendre un peu pour vérifier que le processus démarre bien
    sleep 2
    if ps -p $pid > /dev/null 2>&1; then
        echo -e "${GREEN}[✓] $name démarré (PID: $pid)${NC}"
        echo -e "    Log: $log_file"
        return 0
    else
        echo -e "${RED}[✗] Échec du démarrage de $name${NC}"
        rm -f "$pid_file"
        return 1
    fi
}

# Fonction pour arrêter un processus
stop_daemon() {
    local name=$1
    local pid_file="$PID_DIR/${name}.pid"
    
    if [ ! -f "$pid_file" ]; then
        echo -e "${YELLOW}[!] $name n'est pas en cours d'exécution${NC}"
        return 1
    fi
    
    local pid=$(cat "$pid_file")
    if ps -p $pid > /dev/null 2>&1; then
        echo -e "${BLUE}[*] Arrêt de $name (PID: $pid)...${NC}"
        kill $pid
        sleep 1
        if ps -p $pid > /dev/null 2>&1; then
            kill -9 $pid
        fi
        rm -f "$pid_file"
        echo -e "${GREEN}[✓] $name arrêté${NC}"
    else
        echo -e "${YELLOW}[!] $name n'est plus en cours d'exécution${NC}"
        rm -f "$pid_file"
    fi
}

# Fonction pour afficher le statut
show_status() {
    echo ""
    echo "========================================="
    echo "Statut des processus"
    echo "========================================="
    
    for service in serveur-donnees-1 serveur-donnees-2 serveur-donnees-3 middleware-validation serveur-morpions; do
        local pid_file="$PID_DIR/${service}.pid"
        if [ -f "$pid_file" ]; then
            local pid=$(cat "$pid_file")
            if ps -p $pid > /dev/null 2>&1; then
                echo -e "${GREEN}[✓] $service${NC} (PID: $pid)"
            else
                echo -e "${RED}[✗] $service${NC} (PID file exists but process dead)"
                rm -f "$pid_file"
            fi
        else
            echo -e "${YELLOW}[ ] $service${NC} (non démarré)"
        fi
    done
    echo ""
}

# Fonction pour voir les logs
tail_logs() {
    local name=$1
    local log_file="$LOG_DIR/${name}.log"
    
    if [ -f "$log_file" ]; then
        echo -e "${BLUE}[*] Logs de $name (Ctrl+C pour quitter):${NC}"
        tail -f "$log_file"
    else
        echo -e "${RED}[✗] Aucun log trouvé pour $name${NC}"
    fi
}

# Fonction pour démarrer tous les serveurs de validation
start_all_validation() {
    echo ""
    echo "========================================="
    echo "Démarrage de l'infrastructure 2PC"
    echo "========================================="
    echo ""
    
    start_daemon "serveur-donnees-1" "run-serveur-donnees-1"
    start_daemon "serveur-donnees-2" "run-serveur-donnees-2"
    start_daemon "serveur-donnees-3" "run-serveur-donnees-3"
    
    echo ""
    echo -e "${BLUE}[*] Attente du démarrage des serveurs (3 secondes)...${NC}"
    sleep 3
    
    start_daemon "middleware-validation" "run-middleware-validation"
    
    echo ""
    echo -e "${GREEN}=========================================${NC}"
    echo -e "${GREEN}Infrastructure 2PC démarrée !${NC}"
    echo -e "${GREEN}=========================================${NC}"
    echo ""
    echo "Vous pouvez maintenant lancer le client avec:"
    echo "  mvn exec:java@run-client-validation"
    echo ""
}

# Fonction pour arrêter tous les processus
stop_all() {
    echo ""
    echo "========================================="
    echo "Arrêt de tous les processus"
    echo "========================================="
    echo ""
    
    stop_daemon "middleware-validation"
    stop_daemon "serveur-morpions"
    stop_daemon "serveur-donnees-1"
    stop_daemon "serveur-donnees-2"
    stop_daemon "serveur-donnees-3"
    
    echo ""
}

# Menu principal
show_menu() {
    clear
    echo "========================================="
    echo "TP8 - Validation 2PC et Morpions RMI"
    echo "========================================="
    echo ""
    echo "GESTION DES SERVICES (daemon)"
    echo "  1) Démarrer TOUS les serveurs 2PC (3 serveurs + middleware)"
    echo "  2) Démarrer serveur de données 1"
    echo "  3) Démarrer serveur de données 2"
    echo "  4) Démarrer serveur de données 3"
    echo "  5) Démarrer middleware de validation"
    echo "  6) Démarrer serveur de morpions"
    echo ""
    echo "CLIENTS (interactif)"
    echo "  7) Lancer client de validation (interactif)"
    echo "  8) Lancer client joueur de morpions (interactif)"
    echo ""
    echo "MONITORING"
    echo "  9) Voir le statut des services"
    echo " 10) Voir les logs d'un service"
    echo ""
    echo "ARRÊT"
    echo " 11) Arrêter UN service"
    echo " 12) Arrêter TOUS les services"
    echo ""
    echo "AUTRES"
    echo " 13) Compiler le projet"
    echo " 14) Nettoyer les logs et PIDs"
    echo "  0) Quitter"
    echo ""
}

while true; do
    show_menu
    read -p "Votre choix: " choice
    
    case $choice in
        1)
            start_all_validation
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        2)
            start_daemon "serveur-donnees-1" "run-serveur-donnees-1"
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        3)
            start_daemon "serveur-donnees-2" "run-serveur-donnees-2"
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        4)
            start_daemon "serveur-donnees-3" "run-serveur-donnees-3"
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        5)
            start_daemon "middleware-validation" "run-middleware-validation"
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        6)
            start_daemon "serveur-morpions" "run-serveur-morpions"
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        7)
            echo -e "${BLUE}[*] Lancement du client de validation...${NC}"
            mvn exec:java@run-client-validation
            ;;
        8)
            echo -e "${BLUE}[*] Lancement du client joueur...${NC}"
            mvn exec:java@run-client-morpions
            ;;
        9)
            show_status
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        10)
            echo ""
            echo "Services disponibles:"
            echo "  1) serveur-donnees-1"
            echo "  2) serveur-donnees-2"
            echo "  3) serveur-donnees-3"
            echo "  4) middleware-validation"
            echo "  5) serveur-morpions"
            echo ""
            read -p "Quel service? (nom complet): " service_name
            tail_logs "$service_name"
            ;;
        11)
            echo ""
            read -p "Nom du service à arrêter: " service_name
            stop_daemon "$service_name"
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        12)
            stop_all
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        13)
            echo -e "${BLUE}[*] Compilation du projet...${NC}"
            mvn clean compile
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        14)
            echo -e "${BLUE}[*] Nettoyage des logs et PIDs...${NC}"
            rm -rf "$PID_DIR" "$LOG_DIR"
            mkdir -p "$PID_DIR" "$LOG_DIR"
            echo -e "${GREEN}[✓] Nettoyage terminé${NC}"
            read -p "Appuyez sur Entrée pour continuer..."
            ;;
        0)
            echo ""
            show_status
            if ls "$PID_DIR"/*.pid 1> /dev/null 2>&1; then
                echo -e "${YELLOW}[!] Des services sont encore en cours d'exécution${NC}"
                read -p "Voulez-vous les arrêter avant de quitter? (o/n): " stop_confirm
                if [ "$stop_confirm" = "o" ] || [ "$stop_confirm" = "O" ]; then
                    stop_all
                fi
            fi
            echo "Au revoir!"
            exit 0
            ;;
        *)
            echo -e "${RED}Choix invalide${NC}"
            sleep 1
            ;;
    esac
done
