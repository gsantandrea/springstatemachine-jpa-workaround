# Workaround for JPA repository autowiring problem 

The JpaStateMachineRepositoryClass wrapper class is a temporary workaround for the following problem:
https://github.com/spring-projects/spring-statemachine/issues/511

## How to use

`curl  curl  -H "Content-Type: application/json"   -XPUT localhost:8080/statemachine/sendevent`