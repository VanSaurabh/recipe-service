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

### API Specification
Please check the API specification here:
- [API specification](src/main/resources/api-spec/recipe-manage-openapi.yaml)

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