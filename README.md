# recipe-service
A simple recipe management service

## Description
This is a recipe management service, supports the following operations:

```yaml
Adding recipes
Updating recipes
Removing recipes
Fetching recipes
Search Recipes on whether or not the dish is vegetarian
Search Recipes on the number of servings
Search Recipes on specific ingredients (either include or exclude)
Search Recipes on text search within the instructions
```

### Tech stack and architectural decision

#### Tech stack
```yaml
Language  - Java 17
Framework - Spring Boot
Database  - H2
ResAPIs   - Open API spec (contract first approach)
```
#### Decisions
```yaml
1. To maintain the simplicity of Ids, the Ids are taken as Integers. In production we can go for UUIDs
2. The service is developed as a spring boot app
3. The implementation follows contract first approach using Open API and Plugin to generate Open API
4. Few initial data sets are created in the DB, on application start up
```
### API Specification
Please check the API specification here:
- [API specification](src/main/resources/api-spec/recipe-manage-openapi.yaml)

### Sample Request
Please find sample request here:
- [Sample Requests](src/main/resources/data/data.sql)

### Initial Data set
Please find the initial dataset here:
- [Data Set](src/main/resources/data/data.sql)

### Instruction to build the service
go to the root of the project and execute

```bash
mvn clean verify
```

### Instruction to start the service
go to the root of the project and execute

```bash
mvn spring-boot:run
```

### Instructions to build docker image
go to the root of the project and execute

```bash
docker image build -t recipe-service:latest .
```

### Instructions to run docker image
go to the root of the project and execute, this will expose the service at port localhost:8080

```bash
docker run -p 8080:8080 recipe-service:latest
```