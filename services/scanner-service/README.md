# 🕵️ Scanner Service

![Java](https://img.shields.io/badge/Java-17-blue) ![Scanner](https://img.shields.io/badge/Component-Scanner-lightgrey)

What it is
----------
The scanner-service exposes an external API for triggering asynchronous scans and polling results. It:

- accepts scan requests (tenantId, dsId, input)
- creates an internal ScanTask, executes scanning asynchronously using a pluggable ScannerEngine
- fetches DataSet/policy from the Policy Service via an HTTP client
- persists ScanTask and ScanResult into its database

How to run locally
------------------
From the repo root run the module with Maven (or use Docker Compose):

```powershell
mvn -pl services/scanner-service -am spring-boot:run
```

The scanner will be available on port 8080 by default (docker-compose maps it to 8082 on the host).

Docker Compose
--------------
See top-level `docker-compose.yml`. The scanner service is configured with:

- build context: `./services/scanner-service`
- image: `scanner-service:0.0.1`
- container port 8080 -> host 8082
- environment: `policy.service.url=http://policy-service:8080`

Key implementation notes
-------------------------
- Matching logic is pluggable (interface `ScannerEngine`). A default `KeywordScanner` implements whole-word, case-insensitive matching and returns all matching DataTypes.
- The scanner persists the ScanResult only after the background job completes. The ScanTask status can be PENDING, RUNNING, COMPLETED, FAILED, or CANCELLED.
- The service calls Policy Service internal API to fetch DataSet/policy by dsId.

Quick curl examples
-------------------
Trigger:

```powershell
curl -X POST "http://localhost:8082/api/scans" -H "Content-Type: application/json" -d '{"tenantId":"3fa85f64-5717-4562-b3fc-2c963f66afa6","dsId":"<DATASET_UUID>","input":"some text to scan"}'
```

Poll:

```powershell
curl "http://localhost:8082/api/scans/<SCAN_ID>?tenantId=3fa85f64-5717-4562-b3fc-2c963f66afa6"
```

Troubleshooting
---------------
- If scans never complete, check logs for exceptions contacting Policy Service or DB problems.
- If you see login pages on swagger, check security config or run with security disabled profile.
