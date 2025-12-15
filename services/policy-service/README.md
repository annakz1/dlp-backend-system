# 🛡️ Policy Service

![Java](https://img.shields.io/badge/Java-17-blue) ![Policy](https://img.shields.io/badge/Component-Policy-lightgrey)

policy-service
==============

What it is
----------
Holds Data Sets (policies) enriched with resolved DataType data. This service owns DS persistence and exposes the internal API used by other services.
Responsibilities:
- CRUD for Data Sets (with embedded DataType details)
- When a DataType is updated or deleted (notified by configuration-service), the service scans existing Data Sets and updates/removes embeddable entries accordingly.

How to run locally
------------------
Run with Maven:

```powershell
mvn -pl services/policy-service -am spring-boot:run
```

Docker Compose
--------------
Top-level `docker-compose.yml` builds and runs the policy-service image on host port 8081.

Internal endpoints
------------------
- POST /internal/policies/datatype-updated — called by configuration-service when a DataType is updated; policy-service will scan datasets and update embeddable entries.
- POST /internal/policies/datatype-deleted/{id} — called by configuration-service when a DataType is deleted.

