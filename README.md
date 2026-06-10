# Parent-Child DB Relation

A Spring Boot REST API demonstrating **JPA Joined-Table inheritance** with Oracle DB, MapStruct, and Lombok.

Three entity types — `Parent`, `Child1`, `Child2` — share a common `PARENT` table. Each child type has its own table that holds only its extra columns and joins back to `PARENT` via a foreign key.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.3.5 |
| Persistence | Spring Data JPA / Hibernate |
| Database | Oracle XE 21c |
| Mapping | MapStruct 1.5.5 + Lombok 1.18.30 |
| Containerisation | Docker / Docker Compose |
| API Testing | Bruno CLI 3.x |

---

## Data Model

### Inheritance strategy: `JOINED`

```
PARENT table
┌────┬──────────────┬──────┬────────────┐
│ ID │ NAME (NOT NULL) │ DTYPE │            │
├────┼──────────────┼──────┤            │
│  1 │ Alice         │      │  plain Parent │
│  2 │ Bob           │CHILD1│  → CHILD1  │
│  3 │ Carol         │CHILD2│  → CHILD2  │
└────┴──────────────┴──────┘

CHILD1 table          CHILD2 table
┌───────────┬────────┐  ┌───────────┬─────┐
│ PARENT_ID │ FAMILY │  │ PARENT_ID │ AGE │
├───────────┼────────┤  ├───────────┼─────┤
│     2     │ Smith  │  │     3     │  30 │
└───────────┴────────┘  └───────────┴─────┘
```

- `DTYPE` is a discriminator column (`STRING`) on the `PARENT` table.
- Child tables reference `PARENT` via `PARENT_ID` (FK + PK).
- The `PARENT_SEQ` Oracle sequence drives ID generation.

---

## Quick Start — Docker Compose

Everything runs in containers. No local Java or Node installation needed.

### Start infrastructure (Oracle + App)

```bash
docker-compose up
```

Oracle XE takes ~2 minutes to initialise on the first run. The app waits for Oracle to pass its health check before starting.

### Run the full test pipeline

```bash
docker-compose --profile test up --abort-on-container-exit --exit-code-from bruno
```

Starts Oracle → App → Bruno in dependency order, runs all 15 API tests, then tears down. Exit code is `0` on pass, non-zero on failure — suitable for CI.

### Stop and clean up

```bash
docker-compose down
```

---

## Local Development

### Prerequisites

- Java 17 (MapStruct 1.5.x is incompatible with JDK 21+)
- Maven 3.x
- Docker (for Oracle XE)
- Node.js / npm (for Bruno CLI)

### 1 — Start Oracle XE

```bash
docker run -d \
  --name oracle-xe \
  -p 1521:1521 \
  -e ORACLE_PASSWORD=Oracle123 \
  -e APP_USER=appuser \
  -e APP_USER_PASSWORD=apppassword \
  gvenzl/oracle-xe:21-slim
```

Wait until ready:

```bash
docker logs -f oracle-xe | grep "DATABASE IS READY"
```

### 2 — Start the app

```bash
JAVA_HOME=$(/usr/libexec/java_home -v 17) \
  mvn spring-boot:run -DskipTests
```

The app starts on **http://localhost:8080**. Hibernate auto-creates the schema on first run (`ddl-auto=update`).

### 3 — Run Bruno tests

```bash
npm install -g @usebruno/cli
cd bruno-collection
bru run --env local
```

---

## API Reference

All three resources expose the same five operations:

| Method | Path | Status | Description |
|--------|------|--------|-------------|
| `GET` | `/api/{resource}` | 200 | List all |
| `GET` | `/api/{resource}/{id}` | 200 / 404 | Get by ID |
| `POST` | `/api/{resource}` | 201 | Create |
| `PUT` | `/api/{resource}/{id}` | 200 / 404 | Update |
| `DELETE` | `/api/{resource}/{id}` | 204 / 404 | Delete |

Resources: `parents`, `child1s`, `child2s`

### Request / Response bodies

**Parent**
```json
// POST /api/parents
{ "name": "Alice" }

// Response
{ "id": 1, "name": "Alice" }
```

**Child1** (inherits `id`, `name`; adds `family`)
```json
// POST /api/child1s
{ "name": "Bob", "family": "Smith" }

// Response
{ "id": 2, "name": "Bob", "family": "Smith" }
```

**Child2** (inherits `id`, `name`; adds `age`)
```json
// POST /api/child2s
{ "name": "Carol", "age": 30 }

// Response
{ "id": 3, "name": "Carol", "age": 30 }
```

### Error response

```json
{
  "timestamp": "2026-06-11T10:00:00.000",
  "status": 404,
  "error": "Not Found",
  "message": "Child1 with id 99 not found"
}
```

---

## Bruno Collection

The collection at `bruno-collection/` covers all 15 endpoints (5 per resource).

### Environments

| Environment | `baseUrl` | Use when |
|-------------|-----------|----------|
| `local` | `http://localhost:8080` | Running app via Maven locally |
| `docker` | `http://app:8080` | Running inside Docker Compose |

### Running

```bash
cd bruno-collection

bru run --env local                  # all resources
bru run parents --env local          # one resource only
bru run child1s --env local
bru run child2s --env local
```

### How IDs are managed

The `create-*.bru` requests include a `script:post-response` block that captures the returned `id` into a runtime variable (`{{parentId}}`, `{{child1Id}}`, `{{child2Id}}`). The subsequent get-by-id, update, and delete requests in the same run use these variables automatically — no manual state management needed.

---

## Project Structure

```
.
├── Dockerfile                        # Multi-stage: Maven build → JRE 17 Alpine runtime
├── docker-compose.yml                # oracle + app (default) · bruno (--profile test)
├── .dockerignore
├── pom.xml
├── src/main/
│   ├── resources/application.properties
│   └── java/com/example/parentchilddbrelation/
│       ├── ParentChildDbRelationApplication.java
│       ├── controller/               # ParentController, Child1Controller, Child2Controller
│       ├── dto/                      # ParentDto, Child1Dto, Child2Dto
│       ├── entity/                   # Parent, Child1, Child2  (JOINED inheritance)
│       ├── mapper/                   # BaseMapper<E,D> · per-entity overrides (id ignored on update)
│       ├── repository/               # Spring Data JPA repositories
│       ├── service/                  # Transactional service layer
│       └── exception/                # EntityNotFoundException · GlobalExceptionHandler
└── bruno-collection/
    ├── Dockerfile                    # node:20-alpine + @usebruno/cli
    ├── entrypoint.sh                 # polls app readiness, then runs bru
    ├── bruno.json
    ├── environments/
    │   ├── local.bru
    │   └── docker.bru
    ├── parents/                      # get-all · get-by-id · create · update · delete
    ├── child1s/
    └── child2s/
```
