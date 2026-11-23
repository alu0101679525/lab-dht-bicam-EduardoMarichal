@echo off
REM ==============================================================================
REM Script para construir y ejecutar la aplicacion BiCIAM en Docker (Windows CMD)
REM Practica 5 - Laboratorio de Desarrollo y Herramientas (ULL)
REM ==============================================================================

echo [INFO] Construyendo imagen Docker para BiCIAM (multi-stage build)...
docker build -t biciam:latest .

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo al construir la imagen Docker. Asegurese de que Docker Desktop esta en ejecucion.
    exit /b %ERRORLEVEL%
)

echo [INFO] Ejecutando contenedor BiCIAM...
docker run --rm biciam:latest

echo [INFO] Ejecucion finalizada.
