# TODO Backend Using DynamoDB

## Use cases covered by controllers

```plantuml
@startuml
left to right direction

actor "Planner" as planner

usecase "Create a task" as create
usecase "Retrieve a task by ID" as retrieve
usecase "Retrieve all tasks" as retrieveAll
usecase "Update task status by ID" as update
usecase "Delete a task by ID" as delete

planner --> create
planner --> retrieve
planner --> retrieveAll
planner --> update
planner --> delete
@enduml
```
## Decisions and Questions


## Controller test snippets

**Test create**
```bash
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"caseNumber":"CASE-0001", "title":"title", "description":"Book call with client", "status": "New"}' \
http://localhost:4000/task-manager/create-task
```
**Test Retrieve by ID - last param is ID**
```bash
curl -X GET "http://localhost:4000/task-manager/get-task/1"
```

**Test Retrieve all**
```bash
curl -X GET "http://localhost:4000/task-manager/get-all-tasks" | jq
```
**Test Update by ID**

```bash
curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{"id":1, "caseNumber":"CASE-0001", "title":"UPDATED", "description":"Book call with client", "status": "In Progress", "createdDate":"2025-08-02T18:17:15.174236"}' \
http://localhost:4000/task-manager/update-task
```
**Test Delete by ID**

```bash
curl -X DELETE "http://localhost:4000/task-manager/delete-task/1"
```