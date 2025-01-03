<H2> Read me </H2>

<H3>1. Problem Statement</H3>

A Facility Management System is required to streamline room booking, guest management, and service coordination for a hotel. The system should ensure efficient booking, automated notifications, and service team interactions. Key requirements include:

Room management for multiple categories (Deluxe, Luxury, Luxury Suite) across 10 floors.
Automated notifications for room service, WiFi credentials, and restaurant menu updates.
Real-time updates on room availability and booking history.
Secure data handling for guest details and ID proof uploads.
Integration of multiple microservices for efficient communication.

<H3>2. Approach</H3>

The solution involves a modular architecture built on microservices and asynchronous communication to ensure scalability, reliability, and seamless operations. Key steps:

**Play Framework API:**
Handles REST API requests for room bookings and guest management.
Routes requests to the appropriate service.

**Akka Actor System:**
Manages asynchronous tasks, including notifications and inter-service communication.
Implements a NotificationService to handle automated email tasks.

**Kafka Message Broker:**
Ensures reliable message queuing between microservices.
Topics like BookingNotifications and ServiceUpdates facilitate communication.

**MongoDB Database:**
Stores persistent data, including room availability, guest details, and booking history.
Ensures secure handling of sensitive guest information.

**Microservices:**
Room Booking Microservice for managing room availability.
Email Notification Service for automated email dispatch.

<H3>3. Architecture</H3>

![Architecture Diagram](/Users/navadeep/Downloads/Projects/DayWiseProjects/ScalaTraining/resources/images/forProject/Screenshot 2025-01-03 at 12.25.12 PM.png)

<H3>4. Technologies</H3>

* Backend Framework: Play Framework (Scala)
* Messaging and Asynchronous Tasks: Akka Actor System
* Message Broker: Kafka
* Database: MongoDB
* Build Tool: SBT
* Containerization: Docker
* Testing: ScalaTest
* Email Notifications: Akka Email Actors

<H3>5. Schema</H3>

![ER Flow](/Users/navadeep/Downloads/Projects/DayWiseProjects/ScalaTraining/resources/images/forProject/Screenshot 2025-01-03 at 12.35.33 PM.png)

These are the schemas:

![Schema](/Users/navadeep/Downloads/Projects/DayWiseProjects/ScalaTraining/resources/images/forProject/Screenshot 2025-01-03 at 12.37.17 PM.png)


**Kafka Topics**

BookingNotifications:
* Publishes notifications related to bookings.
ServiceUpdates:
* Sends updates to room service, WiFi, and restaurant teams.