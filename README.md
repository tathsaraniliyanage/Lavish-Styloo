# Lavish Styloo - MVC Architecture 🚀


![Screenshot (476)](https://github.com/user-attachments/assets/e55463a9-c487-4721-b824-3934f7e4ede3)


![Screenshot (457)](https://github.com/user-attachments/assets/025a90b2-a61c-4538-9627-bf2ecf9883b1)


Lavish Styloo is a comprehensive saloon management system designed to streamline the operations of a saloon or spa. It provides an efficient way to manage bookings, payments, employees, customers, orders, treatments, and user authentication. Built using Java and MySQL, the system follows a MVC Architecture for better maintainability and scalability.


## Features 🌟

1. Booking Management:

    - Schedule and manage customer appointments.

    - View, update, or cancel bookings.

2. Payment Management:

    - Record and track payments for services.

    - Generate invoices for customers.

3. Employee Management:

    - Add, update, or remove employee details.

    - Assign employees to specific treatments or bookings.

4. Customer Management:

    - Maintain customer profiles and history.

    - Track customer preferences and past services.

5. Order Management:

    - Manage product orders and inventory.

    - Track order status and delivery.

7. Treatment Management:

    - Add, update, or remove treatment details.

    - Assign treatments to specific bookings.

8. User Authentication:

    - Secure login and registration system.

    - Role-based access control (e.g., Admin, Employee).

9. Database Integration:

    - Uses MySQL for reliable and scalable data storage.

    - Efficiently manages relationships between entities (e.g., customers, bookings, treatments).

10. MVC Architecture:

    - Follows a structured architecture for better code organization:
    - 
        - Model : Manages application logic and rules.

        - View : Handles database interactions and user interfaces.
          
        - Controller : Handles user inputs.
          

## Technologies Used 💡

   - **Programming Language** : Java

   - **Database** : MySQL

   - **Architecture** : MVC Architecture (Model, View, Controller)

   - **Tools** : IntelliJ IDEA, MySQL Workbench, Git


## Installation and Setup 📍

### Prerequisites 🔗

   1. Java Development Kit (JDK) : Ensure JDK 8 or higher is installed.

   2. MySQL Server : Install and configure MySQL.

   3. IDE : Use IntelliJ IDEA or any Java-supported IDE.


### Steps to Run the Project 🔗

1. Clone the Repository:

  ```
  git clone https://github.com/tathsaraniliyanage/Lavish-Styloo.git
  ```

2. Set Up the Database:

   - Import the provided SQL file (lavish_styloo.sql) into MySQL.

   - Update the database connection details in the DatabaseConfig.java file.

3. Run the Application:

   - Open the project in your IDE.

   - Build and run the Main.java file to start the application.

4. Access the System:

   - Use the provided login credentials (e.g., Admin/Employee) to access the system.



---

## Database Schema 🪜

The database consists of the following tables:

  - **users**: Stores user authentication details.

  - **customers**: Stores customer information.

  - **employees**: Stores employee details.

  - **bookings**: Manages appointment bookings.

  - **payments**: Tracks payment details.

  - **treatments**: Stores treatment/service details.

  - **orders**: Manages product orders.



## Screenshots 🖼️
![Screenshot (480)](https://github.com/user-attachments/assets/5ad5eeef-e5e7-4590-bcc4-5534a66d86c2)
![Screenshot (482)](https://github.com/user-attachments/assets/a9c39ff6-01c0-4c27-a445-67916c6b5085)
![Screenshot (453)](https://github.com/user-attachments/assets/a1595a8e-8fad-43c1-845e-d87c20a03034)
![Screenshot (477)](https://github.com/user-attachments/assets/91d1541c-1ed0-4bdd-ab4c-9e3deaf1f296)





## 📚 License

This project is licensed under the [MIT License](LICENSE).
