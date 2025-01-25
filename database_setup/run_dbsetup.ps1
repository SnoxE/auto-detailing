# Configuration
$dockerContainerName = "postgres"   # Name of the Docker container
$postgresUser = "postgres"         # PostgreSQL username
$postgresDb = "postgres"           # Database name
$sqlFile = "dbsetup.sql"           # SQL file name

# Check if the Docker container is running
if (-not (docker ps | Select-String $dockerContainerName)) {
    Write-Host "Error: Docker container '$dockerContainerName' is not running." -ForegroundColor Red
    exit 1
}

# Read the SQL file content
if (-not (Test-Path $sqlFile)) {
    Write-Host "Error: File '$sqlFile' not found." -ForegroundColor Red
    exit 1
}

$sqlContent = Get-Content $sqlFile -Raw

# Execute the SQL file content in the PostgreSQL container
Write-Host "Executing $sqlFile in the container $dockerContainerName..."
$process = docker exec -i $dockerContainerName psql -U $postgresUser -d $postgresDb

# Pass the SQL content to the process
$process.StandardInput.WriteLine($sqlContent)
$process.StandardInput.Close()

# Wait for the process to finish and capture the exit code
$process.WaitForExit()
if ($process.ExitCode -eq 0) {
    Write-Host "SQL script executed successfully." -ForegroundColor Green
} else {
    Write-Host "Failed to execute the SQL script. Check logs for more details." -ForegroundColor Red
}