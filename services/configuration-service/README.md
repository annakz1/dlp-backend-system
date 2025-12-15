# ⚙️ Configuration Service

![Java](https://img.shields.io/badge/Java-17-blue) ![Config](https://img.shields.io/badge/Component-Config-lightgrey)

configuration-service
=====================

What it is
----------
Manages Data Types (DTs) and Data Sets (DSs). Responsibilities:

- CRUD for DataType objects
- CRUD for Data Sets (delegates persistence to Policy Service via internal API)
- When a DataType is updated or deleted, the service notifies the Policy Service so datasets referencing that DataType are updated/cleaned.

How to run locally
------------------
Run with Maven:

```powershell
mvn -pl services/configuration-service -am spring-boot:run
```

Docker Compose
--------------
Top-level `docker-compose.yml` builds and runs the configuration-service image on host port 8080.

Important notes
---------------
- The service uses `PolicyServiceClient` (RestTemplate) to communicate with the Policy Service.
- Notifications to Policy Service are best-effort — if notify fails the update still succeeds locally.

