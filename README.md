# Web Application Development Steps

## Backend Development
Ensure that MySQL is running before proceeding further
### Installing java and Maven

```bash
sudo apt update
sudo apt install openjdk-17-jdk
sudo apt install maven

```


### Clone the git repository for backend  and  modify the application properties

```
git clone  https://github.com/gavika/reference-app-payroll-backend.git
cd reference-app-payroll-backend
vi src/main/resources/application.properties
```
### Replace the MySql parameters with actual values
```
spring.datasource.url=jdbc:mysql://MYSQL_HOST/MYSQL_DATABASE
spring.datasource.username=MYSQL_USER
spring.datasource.password=MYSQL_PASSWORD
```
### Build the jar file and run the backend application
Jar file name may vary
```bash
mvn clean package
java -jar target/payroll-0.0.1-SNAPSHOT.jar
```
### Test

Open any browser and paste 
``` 
http://localhost:8080 
``` 
you will see the welcome text `Hello from the backend of the Payroll application @gavika`


