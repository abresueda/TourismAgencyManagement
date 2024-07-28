Tourism Agency Hotel Management System 
Project Description
Project Purpose: A digital infrastructure has been developed to enable the Tourism Agency's hotel operations and customer reservation processes to be managed more effectively. This system allows the agency to perform tasks that were previously done manually and on paper in a digital environment, making the process easier and faster.
User Roles and Permissions:
Admin:
User Management:
Listing agency employees
Adding
Deleting
Updating
Filtering by user role
Agency Employee (Staff):
Hotel Management:
Listing hotels
Adding hotels
Room Management:
Listing rooms
Adding rooms
Season Management:
Listing seasons
Adding seasons
Price Management
Room Search
Reservation Processes:
Listing reservations
Adding reservations
Deleting reservations
Updating reservations
Functional Requirements:
User Management:
Admin can add and remove users and edit their information.
Users log in to the system with a username and password.
Admin can filter users by role.
Hotel Management:
Adding hotels: Hotel name, address, email, phone, star rating, facility features, pension types.
Listing and editing hotels.
Recording hotel pension types, facility features, and season information.
Season Management:
Adding historical seasons for hotels, which are considered in room pricing.
Defining seasons as two date ranges.
Room Management:
Adding rooms: Hotel, room type, pension type, season, nightly price for adults and children, stock quantity.
Room features: Bed count, square meters, TV, minibar, game console, safe, projector.
Room Search and Reservation Processes:
Searching rooms by date range, city, and hotel name.
Flexible search with dynamic SQL queries.
Reservation process: Customer contact information, guest information (name, surname, national ID).
Listing, updating, and deleting reservations.
Technical Requirements:
Database:
MySQL or PostgreSQL.
GUI Design:
Designed using user-friendly interfaces like Swing or JavaFX.
Database Tables:
user: Admin and agency employee user information.
hotel: Registered hotel information.
season: Seasonal records for hotels (hotel_id).
pension_type: Pension type records for hotels (hotel_id).
room: Room records for hotels (hotel_id, season_id, pension_type_id).
reservation: Reservation records for rooms (room_id).
Project Setup and Usage:
Database Setup:
Install MySQL or PostgreSQL.
Create the tables mentioned above.
Downloading Project Files:
Download the project files from the provided link.
Configuration:
Set the database connection information in the configuration file (e.g., config.properties).
Running the Project:
Open and run the project in your IDE.
Log in to the system as an admin or agency employee.
User Management:
As an admin, add new users and manage existing ones.
Hotel, Room, and Reservation Processes:
As an agency employee, add and manage hotels and rooms.
Perform and manage reservation processes.
Continuation of Project Setup and Usage:
Hotel Management:
Add hotels: Enter necessary information such as hotel name, address, email, phone, star rating, facility features, and pension types.
List and edit existing hotels.
Save information about hotel pension types, facility features, and season details.
Season Management:
Add historical seasons for hotels. These seasons will be considered for room pricing.
Define seasons as two date ranges and list the existing seasons.
Room Management:
Add new rooms: Enter necessary information such as hotel, room type, pension type, season, nightly price for adults and children, and stock quantity.
Specify room features: Bed count, square meters, TV, minibar, game console, safe, projector.
List and edit existing rooms.
Room Search and Reservation Processes:
Search for rooms by date range, city, and hotel name.
Perform flexible searches using dynamic SQL queries to find the most suitable rooms.
For reservation processes, enter customer contact information and guest details (name, surname, national ID).
View, update, and delete reservations.
Note: When downloading project files and configuring database connection information, carefully follow the instructions. After running the project, log in with the required user roles to fulfill all functional requirements.
