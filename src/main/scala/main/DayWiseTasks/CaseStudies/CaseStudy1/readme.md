# Facility Management System (CaseStudy1)

## 1. Problem Statement

A **Facility Management System** is required to streamline room booking, guest management, and service coordination for a hotel. The system ensures efficient operations through automated notifications, real-time updates, and secure data handling.

### Key Features
- Room management for multiple categories (Deluxe, Luxury, Luxury Suite) across 10 floors.
- Automated notifications for room service, WiFi credentials, and restaurant menu updates.
- Real-time updates on room availability and booking history.
- Secure data handling for guest details and ID proof uploads.
- Integration of microservices for efficient communication.

---

## 2. Approach

The system uses a modular microservices architecture with asynchronous communication to ensure scalability and reliability.

### **Key Components**
1. **Play Framework API**:
    - Handles REST API requests for room bookings and guest management.
    - Routes requests to appropriate services.

2. **Akka Actor System**:
    - Manages asynchronous tasks, including notifications and inter-service communication.
    - Implements a `NotificationService` for automated email dispatch.

3. **Kafka Message Broker**:
    - Ensures reliable message queuing between microservices.
    - Topics like `BookingNotifications` and `ServiceUpdates` facilitate inter-service communication.

4. **MongoDB Database**:
    - Stores persistent data, including room availability, guest details, and booking history.
    - Secures sensitive guest information (e.g., ID proofs).

5. **Microservices**:
    - **Room Booking Service**: Manages room availability and booking history.
    - **Email Notification Service**: Automates email dispatch to relevant teams.

---

## 3. Architecture

The architecture ensures modularity and scalability with components for room booking, notifications, and data storage.

![Architecture Diagram](resources/images/forProject/Screenshot%202025-01-03%20at%2012.25.12%20PM.png)

---

## 4. Technologies

- **Backend Framework**: Play Framework (Scala)
- **Messaging and Asynchronous Tasks**: Akka Actor System
- **Message Broker**: Kafka
- **Database**: MongoDB
- **Build Tool**: SBT
- **Containerization**: Docker
- **Testing**: ScalaTest
- **Email Notifications**: Akka Email Actors

---

## 5. Schema

The data schema captures essential information for rooms, guests, and notifications, ensuring consistency and security.

### **Entity Relationship (ER) Diagram**
![ER Diagram](resources/images/forProject/Screenshot%202025-01-03%20at%2012.35.33%20PM.png)

### **Schemas**
![Schema Details](resources/images/forProject/Screenshot%202025-01-03%20at%2012.37.17%20PM.png)

---

## Kafka Topics

1. **BookingNotifications**:
    - Publishes notifications related to bookings.

2. **ServiceUpdates**:
    - Sends updates to room service, WiFi, and restaurant teams.

---
