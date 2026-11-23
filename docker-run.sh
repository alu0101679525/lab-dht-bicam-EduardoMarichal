#!/usr/bin/env bash
# ==============================================================================
# Script para construir y ejecutar la aplicacion BiCIAM en Docker (Bash)
# Practica 5 - Laboratorio de Desarrollo y Herramientas (ULL)
# ==============================================================================
set -e

echo "[INFO] Construyendo imagen Docker para BiCIAM (multi-stage build)..."
docker build -t biciam:latest .

echo "[INFO] Ejecutando contenedor BiCIAM..."
docker run --rm biciam:latest

echo "[INFO] Ejecucion finalizada con exito."
