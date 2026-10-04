$libDir = Join-Path $PSScriptRoot "lib"
if (-Not (Test-Path $libDir)) {
    New-Item -ItemType Directory -Force -Path $libDir | Out-Null
}

$jars = @(
    @{ Url = "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar"; Name = "mysql-connector-java-8.0.33.jar" },
    @{ Url = "https://repo1.maven.org/maven2/com/zaxxer/HikariCP/5.0.1/HikariCP-5.0.1.jar"; Name = "HikariCP-5.0.1.jar" },
    @{ Url = "https://repo1.maven.org/maven2/org/json/json/20240303/json-20240303.jar"; Name = "json-20240303.jar" },
    @{ Url = "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar"; Name = "slf4j-api-1.7.36.jar" },
    @{ Url = "https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.36/slf4j-simple-1.7.36.jar"; Name = "slf4j-simple-1.7.36.jar" },
    @{ Url = "https://repo1.maven.org/maven2/jakarta/servlet/jakarta.servlet-api/6.0.0/jakarta.servlet-api-6.0.0.jar"; Name = "jakarta.servlet-api-6.0.0.jar" }
)

Write-Host "Downloading JAR dependencies..."

foreach ($jar in $jars) {
    $outPath = Join-Path $libDir $jar.Name
    if (-Not (Test-Path $outPath)) {
        Write-Host "Downloading $($jar.Name)..."
        Invoke-WebRequest -Uri $jar.Url -OutFile $outPath
    } else {
        Write-Host "$($jar.Name) already exists."
    }
}

Write-Host "All JARs downloaded successfully."
