# ==============================================================================
# Script para construir y ejecutar la aplicacion BiCIAM en Docker (PowerShell)
# Practica 5 - Laboratorio de Desarrollo y Herramientas (ULL)
# ==============================================================================

Write-Host "[INFO] Construyendo imagen Docker para BiCIAM (multi-stage build)..." -ForegroundColor Cyan
docker build -t biciam:latest .

if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Fallo al construir la imagen Docker. Asegurese de que Docker Desktop esta en ejecucion." -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "[INFO] Ejecutando contenedor BiCIAM..." -ForegroundColor Green
docker run --rm biciam:latest

Write-Host "[INFO] Ejecucion finalizada con exito." -ForegroundColor Green
