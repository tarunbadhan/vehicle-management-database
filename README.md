# Vehicle Management Database System

## Description
This project is a console-based Vehicle Management System developed using Java and SQLite.  
It allows users to manage vehicles, assign them to owners, and maintain service records efficiently.

---

## Features
- View all vehicles  
- Add new vehicles (linked to existing owners)  
- Update vehicle details  
- Delete vehicles  
- View owners  
- View service records  
- Add service records with predefined service types and prices  

---

## Database Structure
The system consists of three main tables:

- **Owner** – stores owner details  
- **Vehicle** – stores vehicle information linked to owners  
- **ServiceRecord** – stores service history of vehicles  

---

## Technologies Used
- Java (JDBC)  
- SQLite  

---

## Sample Data
The system initializes with sample owners, vehicles, and service records to demonstrate functionality.

---

## How to Run
1. Compile the Java files:  
   `javac Main.java DatabaseConnection.java`

2. Run the program:  
   `java Main`

3. The database will be automatically created and populated.  

4. Use the menu to interact with the system.

---

## Author
Tarun Badhan
