
# Supermarket Application
This is a Spring Boot application designed to process supermarket purchases.

### Technologies Used
* Java Development Kit (JDK): Version 18
* Maven: Version 3.8.1
* Spring Boot: Version 2.6.3
* MySQL: Version 8.0.28

### Getting Started
To set up and run the application locally, follow these steps:

#### 1. Clone the Repository:

git clone https://github.com/IzabelaGramovska/supermarket.git

#### 2. Open the Project:

Open your preferred Java IDE and load the cloned repository:

* Navigate to File > Open and select the directory where you cloned the repository.

#### 3. Configure the Application Properties:

Update the database configuration in src/main/resources/application.properties:

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/supermarket_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect

Ensure that the spring.datasource.username and spring.datasource.password match your local MySQL credentials.

#### 4. Run the Application:

Locate the SupermarketApiApplication.java file in the src/main/java directory. Right-click on the file and select Run 'SupermarketApiApplication' to start the application.

### Additional Information
For more details on configuring and running the application, refer to the existing README.md and HELP.md files in the repository.

