# Visitor Management System (CaseStudy2)

## Project Description

The **Visitor Management System (CaseStudy2)** is a backend solution for streamlining visitor check-ins, check-outs, and notifications in a corporate office. It enables reception staff to manage visitor entries while coordinating with internal teams such as IT Support, Host Employees, and Security via automated notifications. The system is built for scalability, reliability, and ease of deployment.

---

## Features

### Visitor Management
- Add visitors with details like name, contact, purpose of visit, host employee, and ID proof.
- Track visitor check-in and check-out times.
- Update or delete visitor information.

### Notifications
- Notify host employees about visitor arrivals via email.
- Notify IT Support to prepare Wi-Fi credentials.
- Notify Security for visitor clearance.
- Automatically stop notifications upon visitor check-out.

### APIs
- RESTful endpoints for managing visitors and notifications.

### Deployment
- Fully containerized with Docker for consistent deployment across environments.

---

## Architecture

Below is the architecture diagram for the Visitor Management System:

![Architecture Diagram](resources/images/forProject/CaseStudy2/Architecture.png "Visitor Management System Architecture")

The system follows a **modular architecture**:
1. **Controllers**:
    - Handle API requests and route them to appropriate services.
2. **Services**:
    - Implement business logic, validations, and data processing.
3. **Repositories**:
    - Manage data storage and CRUD operations.
4. **Actor System**:
    - Asynchronous handling of notifications using Akka.
5. **Kafka Integration**:
    - Reliable and scalable messaging for notifications.

---

## Schema

Below is the ER diagram of the system:

![ER Diagram](resources/images/forProject/CaseStudy2/ER.png "Entity-Relationship Diagram")

### Visitor
- **Fields**:
    - `visitorId`: Unique identifier for each visitor.
    - `name`: Full name of the visitor.
    - `contact`: Visitor's contact details.
    - `purpose`: Reason for the visit.
    - `hostEmployee`: Name or ID of the host employee.
    - `checkInTime`: Visitor's check-in timestamp.
    - `checkOutTime`: Visitor's check-out timestamp.
    - `idProof`: Path or URL of the uploaded ID proof.

### Notification
- **Fields**:
    - `notificationId`: Unique identifier for each notification.
    - `recipient`: Email address or ID of the notification recipient.
    - `message`: Notification content.
    - `timestamp`: Notification creation timestamp.

---

## Workflow

Here’s a flowchart explaining the system’s workflow:

![Workflow Diagram](resources/images/forProject/CaseStudy2/Flowchart.png "Visitor Management System Workflow")

---

## API Endpoints

### Visitor Management
1. `GET /visitors` - Retrieve all visitors.
2. `GET /visitors/:visitorId` - Retrieve details of a specific visitor.
3. `POST /visitors` - Add a new visitor.
4. `PUT /visitors/:visitorId` - Update details of an existing visitor.
5. `PUT /visitors/:visitorId/checkout` - Mark a visitor as checked out.
6. `DELETE /visitors/:visitorId` - Delete a visitor record.

### Notifications
1. `GET /notifications` - Retrieve all notifications.
2. `GET /notifications/:notificationId` - Retrieve a specific notification.
3. `POST /notifications` - Send a new notification.
4. `DELETE /notifications/:notificationId` - Delete a notification.

