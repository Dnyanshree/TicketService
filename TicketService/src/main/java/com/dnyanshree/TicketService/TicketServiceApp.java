package com.dnyanshree.TicketService;

import java.util.Optional;
import java.util.Scanner;

public class TicketServiceApp 
{
	public static void main( String[] args ){

		TicketServiceImpl tickets = new TicketServiceImpl();
		tickets.init(); 
		int choice=0;
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println("Welcome to Broadway Ticket Service System! Choose your option from the menu:");
			System.out.println("1: Find the number of seats available within the venue, optionally by seating level");
			System.out.println("2: Find and hold the best available seats on behalf of a customer, potentially limited to specific levels");
			System.out.println("3: Reserve and commit a specific group of held seats for a customer");
			System.out.println("4: Exit");
			System.out.println("Enter your choice: ");

			choice= sc.nextInt();
			switch (choice) {
			case 1: {
				System.out.println("Do you want to specify the level number for which you want to find the number of available seats?(Y/N) : ");
				char c=sc.next().charAt(0);
				if(c=='Y'){
				System.out.println("Enter the level Number for which you want to find the number of available seats (1,2,3, or 4): ");
				int level = sc.nextInt();
				System.out.println(tickets.numSeatsAvailable(Optional.ofNullable(level)));
				}
				else if(c=='N'){
					System.out.println(tickets.numSeatsAvailable(Optional.ofNullable(null)));
				}else{
					System.out.println("Invalid Choice. Try again!");
				}
				break;
			}
			case 2:{
				System.out.println("Enter the number of seats you want to book (Maximum 5): ");
				int numSeats = sc.nextInt();
				System.out.println("Enter your email Id to send the details of held seats: ");
				String email = sc.next();				
				System.out.println("Do you want to specify the minimum and maximum level number from which you want to hold seats?(Y/N) : ");
			char c=sc.next().charAt(0);
			if(c=='Y'){
				System.out.println("Enter the maximum level Number for which you want to hold seats (1,2,3, or 4): ");
				int maxlevel = sc.nextInt();
				System.out.println("Enter the minimum level Number for which you want to hold seats (1,2,3, or 4): ");
				int minlevel = sc.nextInt();
				tickets.findAndHoldSeats(numSeats, Optional.ofNullable(minlevel), Optional.ofNullable(maxlevel), email);
			}else if(c=='N'){
					tickets.findAndHoldSeats(numSeats, Optional.ofNullable(null), Optional.ofNullable(null), email);
				
			}else{
				System.out.println("Invalid details. Try Again!");
			}
				break;
			}
			case 3:{
				System.out.println("Enter Seat Hold ID to reserve the seats: ");
				int id= sc.nextInt();
				System.out.println("Enter your email Id to send the details of the reserved seats:");
				String email = sc.next();	
				tickets.reserveSeats(id, email);
				break;
			}
			case 4:{
				System.out.println("Thank you for using the Broadway Ticket Service! See you again..");
				break;
			}
			default:{
				System.out.println("Please enter a valid menu option! Try again!");
				break;
			}
			}
		}while(choice!=4);
		
		sc.close();
		
		tickets.clearAllInits();
	
	}
}
