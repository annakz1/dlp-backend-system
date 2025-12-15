dlp-backend-system 🚀
===================

![Java](https://img.shields.io/badge/Java-17-blue) ![Docker](https://img.shields.io/badge/Docker-enabled-lightgrey) 

Overview
--------
This repository contains a small multi-service DLP backend consisting of:

- configuration-service (manages DataTypes and Data Sets)
- policy-service (stores enriched Data Sets / policies and serves internal API)
- scanner-service (external API that triggers scans and stores results)
- core-models (shared DTOs used across services)

This README explains how to build, run, and test the services locally using Maven and Docker Compose.

Prerequisites
-------------
- Java 17 (OpenJDK 17)
- Maven 3.6+
- Docker and docker-compose
- (Optional) curl / httpie for quick API tests

Notes about Java: the project modules are compiled with Java 17. If you use a different JDK, set JAVA_HOME accordingly.

Build (fast)
------------
To build the Java artifacts (skip tests to speed up):

```powershell
mvn clean package -DskipTests
```

Run (Docker Compose)
---------------------
The repository includes a `docker-compose.yml` that builds each service and starts a local Postgres DB used by all services.

Start all services (rebuild images):

```powershell
docker-compose up --build
```

- configuration-service: http://localhost:8080
- policy-service: http://localhost:8081
- scanner-service: http://localhost:8082

Stop:

```powershell
docker-compose down
```

Run single service locally (useful while developing)
----------------------------------------------------
You can run a single service with Maven (without Docker). Example: run the scanner-service locally on port 8080 inside its module:

```powershell
mvn -pl services/scanner-service -am spring-boot:run
```

If you run services locally and others in Docker, set the appropriate `policy.service.url` environment variable so services can reach each other (the docker-compose config also sets this variable for the containers).

Quick API smoke tests (scanner)
-------------------------------
Trigger a scan (replace tenantId and dsId with valid UUIDs):

```powershell
curl -X POST "http://localhost:8082/api/scans" -H "Content-Type: application/json" -d '{"tenantId":"3fa85f64-5717-4562-b3fc-2c963f66afa6","dsId":"<DATASET_UUID>","input":"This is a secret token: password"}'
```

Poll for result:

```powershell
curl "http://localhost:8082/api/scans/<SCAN_ID>?tenantId=3fa85f64-5717-4562-b3fc-2c963f66afa6"
```

Configuration (DataType / DataSet)
----------------------------------
- configuration-service exposes endpoints to manage DataTypes and DataSets; it will call the policy-service internal API to persist/propagate DataSets.
- Policy Service owns Data Set persistence. When you update or delete a DataType, the configuration-service notifies policy-service so it can update dataset policies.

Troubleshooting
---------------
- Java version errors: make sure `java -version` reports OpenJDK 17. If not, install JDK 17 and set JAVA_HOME.
- If you see authentication pages on swagger UI, ensure security is disabled in your current profile or use the configured login.
- If scans never complete, check scanner-service logs for exceptions contacting policy-service or DB issues.
- If services cannot reach each other, check the `policy.service.url` environment variable is set
