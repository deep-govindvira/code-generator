# Auto Backend File Generator (C++)

This project is a **C++ program** that helps backend developers quickly scaffold required files for a new entity (like `Todo`) when working with Java Spring Boot and AWS DynamoDB. It reduces the repetitive work of creating configuration files, Java model/controller classes, and YAML configurations.

## 📦 Generated Files

After taking input from the user, the following files are automatically generated:

- `application.yaml`  
- `Todo-api.yaml`  
- `Todo-plugin.xml`  
- `Todo.java`  
- `Todo.json`  
- `TodoController.java`  
- `TodoMapperConfig.java`  
- `ModelMapperConfig.java`  
- `DynamoDBConfig.java`  
- `pom.xml`

## 🧠 Input Example

```txt
Enter Item: Todo  
Enter number of fields: 4  
Enter field names: id title description isCompleted
````

## 🔧 Technologies Targeted

* Java (Spring Boot)
* AWS DynamoDB
* REST API (Swagger/OpenAPI)
* Maven (`pom.xml`)
* YAML configuration files

## 📁 Use Case

Ideal for developers who:

* Frequently create new microservices or entities
* Want to automate boilerplate backend setup
* Work in environments using Spring Boot and DynamoDB

