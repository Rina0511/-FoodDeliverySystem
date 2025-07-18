# -FoodDeliverySystem

System Flow:

1. **Customer Registration & Login**

   * Data stored in 'users' table with role = 'customer'
   * Login handled by 'login.php' via HTTP POST

2. **Menu Display (Hardcoded in Java GUI)**

   * No backend call
   * Images and prices are built into the 'MenuGUI.java'

3. **Placing Orders**

   * Selected items and total price passed to 'place_order.php'
   * Stored in 'order'` table with:

     * 'customer_id', 'items', 'total', 'payment_status = paid', 'delivery_status = pending'

4. **Rider Login & View Tasks**

   * Rider logs in (role = 'rider') using same 'login.php' (sent via JSON)
   * 'get_orders_rider.php' fetches:

     * Pending or assigned orders (with address from 'users' table)
     * Includes Google Maps 'Track' link for address

5. **Rider Accepts Order**

   * Click "Accept" → 'accept_order.php' assigns 'assigned_rider' and updates status

6. **Rider Marks as Delivered**

   * Click "Deliver" → 'mark_delivered.php' sets 'delivery_status = delivered'

# Oriental Coffee Food Delivery System

This is a Java + PHP + MySQL food delivery system built for BITP 3123 (Distributed Application Development). It simulates a basic food ordering process with two applications — a Customer App and a Rider App — both integrated with a common backend API.


**Project Structure OrientalCoffee**
**CustomerApp (Java Swing application for customers)**
Login.java
Register.java
MenuGUI.java (Static menu with hardcoded items and images)
PlaceOrderGUI.java
BackendConnector.java (Handles HTTP requests to backend)
Main.java (Main launcher class)
NotificationHelper.java (Popup messages for actions)
Payment.java (Handles payment status)
TrackOrderGUI.java (Displays delivery tracking via Google Maps)
Receipt.java (Displays order receipt)

**RiderApp (Java Swing application for riders)**
RiderLogin.java
RegisterRider.java
ViewTasks.java (Displays and manages delivery tasks)
UpdateStatus.java (Marks order as delivered)
Main.java (Main launcher class)
BackendConnectorRider.java (Handles HTTP requests for rider actions)

**FoodDelivery_Backend (PHP API backend)**
login.php
register.php
place_order.php
get_orders_rider.php
accept_order.php
mark_delivered.php
db_config.php
update_payment_status.php
get_orders.php (Returns customer-specific order data)

**Database**
food_deliverysystem.sql (SQL dump file containing tables and data for the project)


##  Features

### Customer App
- Register/Login
- Static food menu (images + prices)
- Place orders with total calculation
- View payment confirmation
- Address stored for delivery tracking

### Rider App
- Rider login and registration
- View list of 'pending' and 'assigned' orders
- Accept delivery tasks
- Mark orders as delivered
- Track delivery location using Google Maps (customer address)

### Backend (PHP + MySQL)
- RESTful API using HTTP GET/POST
- JSON support for Rider App
- Passwords hashed securely
- Uses one 'users' table and one 'orders' table
- Foreign key: orders.customer_id → users.id

##  Technologies Used
- Java Swing (GUI)
- PHP (REST API)
- MySQL (phpMyAdmin)
- Google Maps (Tracking via address)
- Apache (XAMPP)

##  Security
- Passwords hashed using 'password_hash()' / 'password_verify()'
- Prepared statements used to avoid SQL injection
- Backend role-based validation

##  Project Requirements Covered

| Requirement                         | Status  |
|-------------------------------------|---------|
| 2 frontend apps                     | Yes     |
| 1 backend (API + DB)                | Yes     |
| RESTful API integration             | Yes     |
| Business flow (order → delivery)    | Yes     |
| Database design with ERD            | Yes     |
| Secured backend & validation        | Yes     |
| Google Maps API integration         | Yes     |
| Commercial value demonstrated       | Yes     |

##  Team Roles

| Member       | Role                         |
|--------------|------------------------------|
| Ezzah        | Customer App – Login/Menu    |
| Izzati       | Customer App – Order/Map     |
| Thrisha      | Rider App – Login/Tasks      |
| Kayla        | Rider App – Status/Track     |
| Fazrina      | Backend – PHP APIs           |
| Kaminishvary | Backend – DB Design/Security |

##  Setup Instructions

1. Import 'food_deliverysystem.sql' into phpMyAdmin
2. Place PHP files in 'htdocs/FoodDelivery_Backend'
3. Run Java applications in Eclipse or IntelliJ
4. Make sure Apache and MySQL are running in XAMPP
5. Customize your Google Maps tracking if needed

---

##  License

This project is created for academic purposes under BITP 3123 (Utem, 2025).
