# Set default source and destination paths
$sourcePath = "$env:APPDATA\.minecraft\libraries"
$destinationPath = Join-Path $PSScriptRoot "libs"

# Retrieve all JAR files in the source folder and its subfolders
$jarFiles = Get-ChildItem -Path $sourcePath -Filter *.jar -Recurse

# Copy each JAR file to the destination folder
foreach ($jarFile in $jarFiles) {
    $destinationFile = Join-Path $destinationPath $jarFile.Name
    Copy-Item $jarFile.FullName -Destination $destinationFile
}

Write-Host "JAR files copied successfully."
