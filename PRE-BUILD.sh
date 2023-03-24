#!/bin/bash

# Set default source and destination paths
sourcePath="$HOME/.minecraft/libraries"
destinationPath="$(dirname "$0")/libs"

# Prompt user to confirm or enter custom source and destination paths
read -p "Copy JAR files from '$sourcePath' to '$destinationPath'? (Y/N) " confirm
if [[ $confirm =~ ^[Nn]$ ]]; then
    read -p "Enter source folder path: " sourcePath
    read -p "Enter destination folder path: " destinationPath
fi

# Retrieve all JAR files in the source folder and its subfolders
jarFiles="$(find "$sourcePath" -name '*.jar')"

# Copy each JAR file to the destination folder
for jarFile in $jarFiles; do
    destinationFile="$destinationPath/$(basename "$jarFile")"
    cp "$jarFile" "$destinationFile"
done

echo "JAR files copied successfully."
