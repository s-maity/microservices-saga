### Saga design pattern
#### Choreography or Orchestration
https://camunda.com/blog/2023/02/orchestration-vs-choreography/

Choreography: 
choreography refers to the coordination and organization of interactions between different software components or systems. It is a design pattern that enables the development of distributed systems where the components interact with each other through a set of well-defined interfaces, without requiring a central authority or coordinator.

Orchestration: 	
Orchestration refers to the coordination and management of the interactions and dependencies between different software components or systems. It is a design pattern that enables the development of distributed systems where a central authority or coordinator is responsible for managing and coordinating the actions of the different components.


Choreography vs Orchestration : See the pic in above link

----------
- This implementation

![](microservices-saga.drawio.png)

Booking Service: End user books for travel. Enter details. 
It calls for payment service and wait for feedback and update booking status accordingly

Payment Service: Calls Reservation service and wait for feedback. Based on the feedback received 
it updates payment status and send feedback to Booking service

Reservation Service: Reserve hotel, cab etc. It sends feedback to payment service

-----
 ### Run in local 
 - JDK 17
 - Kafka topic : Here I have used cloudkarafka free account
![img.png](img.png)
 - H2 Database
    - Booking db: http://localhost:8010/h2-console
    - Payment db: http://localhost:8011/h2-console
    - Reservation db: http://localhost:8012/h2-console
    - 
 - Test Success scenarios 
URL: http://localhost:8010/api/v1/booking [POST]
`{
"guestName": "Sudipta Maity",
"destination": "Darjeeling",
"startDate": "2023-10-15",
"endDate": "2023-10-16",
"noOfGuests": 3
}`
 - Test Payment failure
   `{
   "guestName": "Sudipta Maity",
   "destination": "Darjeeling",
   "startDate": "2023-10-15",
   "endDate": "2023-10-16",
   "noOfGuests": 1
   }`
  - Test Reservation failure
     `{
     "guestName": "Sudipta Maity",
     "destination": "Darjeeling",
     "startDate": "2023-10-15",
     "endDate": "2023-10-16",
     "noOfGuests": 13
     }`

-----
Note: Implementing SAGA (Choreography) is not very simple. Code is not very clean nad 
maintainable. Maybe we should look for some framework like Camunda ? 

Here I assume all the compensating transactions are non failure.
How to handle the failure of compensating transactions in SAGA ?

