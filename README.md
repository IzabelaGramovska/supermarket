Spring Boot application for processing Supermarket purchases.

Technologies:

JDK version 18
Maven 3.8.1
Spring Boot 2.6.3
MySQL 8.0.28

Initialization

Firstly you need to clone the git repository:

git clone https://github.com/IzabelaGramovska/supermarket

After completion of this guide you need to open your Java editor and load clonned resources

- File -> Open -> {path to the directory where you clone this repository}

- Go to `resources/application.properties` and add properties e.g. 

- ```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/supermarket_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect

Go to SupermarketApiApplication.java

Click right button and select Run 'Application'

