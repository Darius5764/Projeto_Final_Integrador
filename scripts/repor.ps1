# Uso: .\repor.ps1 -Ficheiro C:\Backups\portal-ecl\portal_ecl_2026-11-25_1300.sql.zip [-Base portal_ecl_teste]
param(
    [Parameter(Mandatory = $true)] [string]$Ficheiro,
    [string]$Base = "portal_ecl"
)
$MariaDbBin = "C:\Program Files\MariaDB 11.4\bin"
$tmp = Join-Path $env:TEMP "repor_portal"
Remove-Item $tmp -Recurse -Force -ErrorAction SilentlyContinue
Expand-Archive -Path $Ficheiro -DestinationPath $tmp
$sql = (Get-ChildItem $tmp -Filter *.sql | Select-Object -First 1).FullName.Replace('\', '/')

# pede a password do root
& "$MariaDbBin\mariadb.exe" -u root -p -e "CREATE DATABASE IF NOT EXISTS $Base CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; USE $Base; SOURCE $sql;"
Remove-Item $tmp -Recurse -Force
Write-Output "Reposto $Ficheiro em $Base"
