
$MariaDbBin  = "C:\Program Files\MariaDB 11.4\bin"
$Destino     = "C:\Backups\portal-ecl"
$Credenciais = "C:\Backups\backup.cnf"

New-Item -ItemType Directory -Force -Path $Destino | Out-Null
$data = Get-Date -Format "yyyy-MM-dd_HHmm"
$sql = Join-Path $Destino "portal_ecl_$data.sql"

& "$MariaDbBin\mariadb-dump.exe" "--defaults-extra-file=$Credenciais" --single-transaction "--result-file=$sql" portal_ecl
if ($LASTEXITCODE -ne 0) { Write-Error "O backup falhou."; exit 1 }

Compress-Archive -Path $sql -DestinationPath "$sql.zip" -Force
Remove-Item $sql

# guardar só os últimos 7 dias
Get-ChildItem $Destino -Filter *.zip | Where-Object LastWriteTime -lt (Get-Date).AddDays(-7) | Remove-Item
Write-Output "Backup criado: $sql.zip"
