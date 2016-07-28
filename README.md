### TicketService Project

#####Problem Statement:

- Find the number of seats available within the venue, optionally by seating level
  Note: available seats are seats that are neither held nor reserved.
- Find and hold the best available seats on behalf of a customer, potentially limited to specific levels
  Note: each ticket hold should expire within a set number of seconds.
- Reserve and commit a specific group of held seats for a customer
  (No GUI and/or database used)

#####Data Structure Used:

1. Venuel levels are predefined in an Enum with total rows and columns for each level 
2. A map is used to store all the venue seats data with key as venue level enum and value as Arraylist of Seat object
3. Seat object contains details - row number, column number integer fields, boolean fields for Hold and Reserve status, getter and setter methods, parameterised constructor and toString method
4. Seats that are on hold are reserved are stored in a SeatHold object that contains details - seatHoldId integer field, customer email and confirmation code as string fields and an arraylist of Seat object with the seats that are on hold or reserved
5. Timer, TimerTask used to run background thread to count the seconds once a hold is acquired and remove the hold after 60 seconds if not reserved

#####Assumptions made:

1. User can hold/reserve a maximum of 5 seats at a time
2. Hold on seats will only last for 60 seconds before expiring. So seats need to be reserved within 10 seconds of holding them

#####Steps to Configure and Run the project on Command Line 
  (Make sure you have latest Maven, JDK/JRE 1.8 installed in your machine to run this project):

1. Download/Clone the project from here: 
    https://github.com/Dnyanshree/TicketServiceProject
2. Open Command prompt and goto location on your machine where TicketService folder is located
3. Run Command: `mvn package`
    Make sure you get BUILD SUCCESS
    This command will build jar file of this project in the Target folder of the project and also run the test cases
4. Finally, to run the project, run command: 
    `java -cp target/TicketService-0.0.1.jar com.dnyanshree.TicketService.TicketServiceApp`
