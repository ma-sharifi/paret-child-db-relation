# Parent-Child DB Relation

Spring Boot REST API demonstrating JPA joined-table inheritance with Oracle DB.  
Three entities share a `parent` table — `Parent`, `Child1` (adds `family`), and `Child2` (adds `age`).

## Prerequisites

| Tool | Version |
|------|---------|
| Docker | any recent version |
| Java 17 | local run only |
| Maven 3.x | local run only |
| Node.js / npm | Bruno CLI local run only |

---

## Option A — Docker Compose (recommended)

Everything runs in containers: Oracle XE, the Spring Boot app, and optionally Bruno for testing.

### Start the infrastructure (Oracle + App)

```bash
docker-compose up
```

Oracle takes ~2 minutes to initialise on first run. The app waits for Oracle to be healthy before starting.

### Run the full test pipeline

```bash
docker-compose --profile test up --abort-on-container-exit --exit-code-from bruno
```

This starts Oracle → App → Bruno in order, runs all 15 API tests, then stops everything. The exit code mirrors Bruno's result (`0` = all passed).

### Stop and clean up

```bash
docker-compose down
```

---

## Option B — Run locally

### 1. Start Oracle XE via Docker

```bash
docker run -d \
  --name oracle-xe \
  -p 1521:1521 \
  -e ORACLE_PASSWORD=Oracle123 \
  -e APP_USER=appuser \
  -e APP_USER_PASSWORD=apppassword \
  gvenzl/oracle-xe:21-slim
```

Wait until the DB is ready:

```bash
docker logs -f oracle-xe | grep "DATABASE IS READY"
```

### 2. Start the Spring Boot App

> **Important:** Use Java 17. MapStruct 1.5.x is incompatible with JDK 21+.

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk/Contents/Home \
  mvn spring-boot:run -DskipTests
```

The app starts on **http://localhost:8080**.  
Hibernate auto-creates the schema (`ddl-auto=update`) on first run.

### 3. Run Bruno tests locally

```bash
npm install -g @usebruno/cli

cd bruno-collection
bru run --env local          # all 15 requests
bru run parents --env local  # only parent requests
```

---

## API Endpoints

| Resource | Base Path |
|----------|-----------|
| Parent   | `/api/parents` |
| Child1   | `/api/child1s` |
| Child2   | `/api/child2s` |

| Method | Path | Description |
|--------|------|-------------|
| GET    | `/api/{resource}` | List all |
| GET    | `/api/{resource}/{id}` | Get by ID |
| POST   | `/api/{resource}` | Create |
| PUT    | `/api/{resource}/{id}` | Update |
| DELETE | `/api/{resource}/{id}` | Delete |

### Example payloads

**Parent** — `POST /api/parents`
```json
{ "name": "Alice" }
```

**Child1** — `POST /api/child1s`
```json
{ "name": "Bob", "family": "Smith" }
```

**Child2** — `POST /api/child2s`
```json
{ "name": "Carol", "age": 30 }
```

---

## Bruno collection

The collection lives in `bruno-collection/` with two environments:

| Environment | `baseUrl` | Usage |
|-------------|-----------|-------|
| `local` | `http://localhost:8080` | local Maven run |
| `docker` | `http://app:8080` | Docker Compose pipeline |

The `create-*.bru` requests capture the returned `id` into `{{parentId}}` / `{{child1Id}}` / `{{child2Id}}` via `script:post-response`, so update and delete always use the ID from that run — no manual state management needed.

---

## Project Structure

```
.
├── Dockerfile                   # Multi-stage build (Maven 3.9 + JRE 17 Alpine)
├── docker-compose.yml           # oracle + app (default) + bruno (--profile test)
├── src/main/java/com/example/parentchilddbrelation/
│   ├── controller/              # REST controllers
│   ├── dto/                     # DTOs
│   ├── entity/                  # JPA entities (joined-table inheritance)
│   ├── mapper/                  # MapStruct mappers
│   ├── repository/              # Spring Data JPA repositories
│   ├── service/                 # Business logic
│   └── exception/               # GlobalExceptionHandler, EntityNotFoundException
└── bruno-collection/
    ├── Dockerfile               # node:20-alpine + @usebruno/cli
    ├── entrypoint.sh            # waits for app, then runs bru
    ├── bruno.json
    ├── environments/
    │   ├── local.bru
    │   └── docker.bru
    ├── parents/                 # 5 requests
    ├── child1s/                 # 5 requests
    └── child2s/                 # 5 requests
```
