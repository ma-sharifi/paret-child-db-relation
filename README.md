# Parent-Child DB Relation

Spring Boot REST API demonstrating JPA joined-table inheritance with Oracle DB.  
Three entities share a `parent` table — `Parent`, `Child1` (adds `family`), and `Child2` (adds `age`).

## Prerequisites

| Tool | Version |
|------|---------|
| Java | 17 |
| Maven | 3.x |
| Docker | any recent version |
| Node.js / npm | for Bruno CLI |

---

## Running the Project

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

Wait until the DB is ready (check logs):

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
Hibernate will auto-create the schema (`ddl-auto=update`) on first run.

### 3. Verify

```bash
curl http://localhost:8080/api/parents
```

---

## API Endpoints

All endpoints follow the same CRUD pattern for three resources:

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

## Running the Bruno Collection

[Bruno](https://www.usebruno.com/) is a Git-friendly API client. The collection lives in `bruno-collection/`.

### Install Bruno CLI

```bash
npm install -g @usebruno/cli
```

### Run all requests

```bash
cd bruno-collection
bru run --env local
```

### Run a single folder

```bash
bru run parents --env local
bru run child1s --env local
bru run child2s --env local
```

### Environment variables (`environments/local.bru`)

| Variable | Default | Description |
|----------|---------|-------------|
| `baseUrl` | `http://localhost:8080` | API base URL |
| `parentId` | `1` | Used by get-by-id before a create; auto-updated after create |
| `child1Id` | `1` | Same as above |
| `child2Id` | `1` | Same as above |

The `create-*.bru` requests use a `script:post-response` block to capture the newly created ID into the matching variable automatically, so update and delete always use the correct ID from the same run.

---

## Project Structure

```
src/main/java/com/example/parentchilddbrelation/
├── controller/      # REST controllers (ParentController, Child1Controller, Child2Controller)
├── dto/             # DTOs (ParentDto, Child1Dto, Child2Dto)
├── entity/          # JPA entities with joined-table inheritance
├── mapper/          # MapStruct mappers (BaseMapper + per-entity overrides)
├── repository/      # Spring Data JPA repositories
├── service/         # Business logic
└── exception/       # GlobalExceptionHandler, EntityNotFoundException

bruno-collection/
├── bruno.json
├── environments/local.bru
├── parents/         # 5 requests: get-all, get-by-id, create, update, delete
├── child1s/         # 5 requests
└── child2s/         # 5 requests
```
