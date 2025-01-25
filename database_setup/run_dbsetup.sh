$dockerContainerName = "postgres"
$postgresUser = "postgres"
$postgresDb = "postgres"
$sqlFile = "dbsetup.sql"


if (-not (docker ps | Select-String $dockerContainerName)) {
    Write-Host "Error: Docker container '$dockerContainerName' is not running." -ForegroundColor Red
    exit 1
}


Write-Host "Executing $sqlFile in the container $dockerContainerName..."
docker exec -i $dockerContainerName psql -U $postgresUser -d $postgresDb -f /dev/stdin < $sqlFile


if ($LASTEXITCODE -eq 0) {
    Write-Host "SQL script executed successfully." -ForegroundColor Green
} else {
    Write-Host "Failed to execute the SQL script. Check logs for more details." -ForegroundColor Red
}