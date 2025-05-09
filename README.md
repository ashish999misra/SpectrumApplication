Customer Rewards Calculator
********************

This Spring boot Application Calculates monthly and total reward points. It uses simple reward logic and stores data in an in-memory H2 database.


********************


The TechStack used is Spring Boot, Spring Web MVC, Spring Data JPA, H2 In-Memory, Maven, Junit + Mockito

**********************


Project Structure

RewardsApplication.java

controller -> RewardsController.java

dto -> CustomerMonthlyPoints.java, CustomerPoints.java, CustomerReward.java

exception -> TransactionNotFoundException.java, GlobalExceptionHandler.java

model -> CustomerTransaction.java, CustomerReward.java

repository -> TransactionRepository.java

service -> RewardsService.java


*************************

How to Build and Run

mvn clean install

mvn spring-boot:run

or run RewardsApplication.java from your IDE

**************************

Access H2 Console

Open: http://localhost:8081/h2-console

JDBC URL: jdbc:h2:mem:testdb

User: sa
Password: (leave blank)


******************************
Sample Data to run in H2-Console

```sql
INSERT INTO customer_transaction(customer, amount, date) VALUES ('Rahul', 120.0, '2024-01-15');
INSERT INTO customer_transaction(customer, amount, date) VALUES ('Rahul', 75.0, '2024-01-20');
INSERT INTO customer_transaction(customer, amount, date) VALUES ('Ravi', 105.0, '2024-02-14');
INSERT INTO customer_transaction(customer, amount, date) VALUES ('Ravi', 130.0, '2024-03-03');
```


*********************************

Rest API usage

GET /api/v1/rewards


http://localhost:8081/api/v1/rewards


Example Response:

```json
[
    {
        "customer": "Rahul",
        "monthlyPoints": {
            "JANUARY": 115
        },
        "totalPoints": 115
    },
    {
        "customer": "Ravi",
        "monthlyPoints": {
            "MARCH": 110,
            "FEBRUARY": 60
        },
        "totalPoints": 170
    }
]
```


**************************
GET /api/v1/customerTotalPoints

http://localhost:8081/api/v1/customerTotalPoints?customer=Rahul

Example Response:

```json
{
    "customer": "Rahul",
    "totalPoints": 115
}
```

********************************************
GET /api/v1/customerMonthlyPoints

http://localhost:8081/api/v1/customerMonthlyPoints?customer=Ravi&from=2024-01-01&to=2025-02-27

Example Response:

```json
{
    "customer": "Ravi",
    "monthlyPoints": {
        "MARCH": 110,
        "FEBRUARY": 60
    },
    "totalPoints": 170
}
```

*********************************************
Postman folder in the project directory has the postman collection for API testing







