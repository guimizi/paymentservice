# paymentservice
## Payment service challenge
This is a programming challenge to build a payment system in order to process charges and payments.
In order to provide high availability it process payments in asynchronous fashion via an active mq.

## Technologies involved

* Java 8
* Maven
* Spring Boot
* Spring Data JPA
* Spring integration
* Hibernate
* H2 DB
* Swagger
* Active MQ

## Prerequisite
It needs an Active MQ running locally on port 61616.(See https://hub.docker.com/r/rmohr/activemq)

## Compile

```bash
mvn clean install
```

## Run
```bash
mvn spring-boot:run
```
By default, the application will run on port 8080.

## Usefull local links
* [Swagger](http://localhost:8080/swagger-ui.html)
* [H2 DB Console](http://localhost:8080/h2-console) - user: sa - pass: sa

## API Details

### Payments 
The payments are processed in an async way. Payment request are sent to active mq for further processing.

Payments can have four states:
* NEW - When it is created
* ACCEPTED - If payment is lesser or equal than total debt.
* REJECTED - If payment is greater than total debt.
* PROCESSED - Once the payment is imputed against charges.

There is an inbound channel that reads from an active mq in order to obtain the payment creation requests.
There is a message handler that parse the incoming payment request and start the processing.
First by saving the payment to the database, and then checking if the request can be accepted or should be rejected.
In case to be accepted, the payment is imputed to matching charges. Finally the payment is marked as processed.

### Charges
The charges are processed synchronously.

## Author

Guido Zibecchi



