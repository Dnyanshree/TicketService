package com.dnyanshree.TicketService;

import java.util.Optional;
import java.util.Scanner;

public class TicketServiceApp 
{
	public static void main( String[] args ){

		TicketServiceImpl tickets = new TicketServiceImpl();
		tickets.init(); 
		int choice=0;
		String email = null;
		SeatHold seats= new SeatHold();
		Scanner sc = new Scanner(System.in);
		do{
			System.err.println("\n**************************************************************"
					+"\nChoose your option:"
					+"\n1: Find the number of seats available"
					+"\n2: Find and hold the best available seats"
					+"\n3: Reserve the seats"
					+"\n4: Exit"
					+"\nEnter your choice:\n ");

			choice= sc.nextInt();
			
			switch (choice) {
			case 1: {
				System.out.println("Do you want to specify the level number?(Y/N) : ");
				char c=sc.next().charAt(0);
				if(c=='Y' || c=='y'){
				System.out.println("Enter the level Number (1,2,3, or 4): ");
				int level = sc.nextInt();
				System.out.println("Available Seats: "+tickets.numSeatsAvailable(Optional.ofNullable(level))+"\n");
				}
				else if(c=='N' || c=='n'){
					System.out.println("Available Seats: "+tickets.numSeatsAvailable(Optional.ofNullable(null))+"\n");
				}else{
					System.out.println("Invalid Choice. Try again!");
				}
				break;
			}
			case 2:{
				System.out.println("Enter the number of seats (Maximum 5): ");
				int numSeats = sc.nextInt();
				System.out.println("Enter your email Id: ");
				email = sc.next();				
				System.out.println("Do you want to specify level numbers?(Y/N) : ");
			char c=sc.next().charAt(0);
			
			if(c=='Y' || c=='y'){
				System.out.println("Enter the maximum level Number(1,2,3, or 4): ");
				int maxlevel = sc.nextInt();
				System.out.println("Enter the minimum level Number(1,2,3, or 4): ");
				int minlevel = sc.nextInt();				
				seats =tickets.findAndHoldSeats(numSeats, Optional.ofNullable(minlevel), Optional.ofNullable(maxlevel), email);	
				
			}else if(c=='N' || c=='n'){
				seats =tickets.findAndHoldSeats(numSeats, Optional.ofNullable(null), Optional.ofNullable(null), email);
				
			}else{
				System.out.println("Invalid details. Try Again!");
			}
			if(seats!=null){
			System.out.println("Seats on hold for 60 seconds: ");
			System.out.println("Level Number: "+seats.getLevelNum());
			for (Seat s : seats.SeatList) {
				System.out.println("Row: "+s.getRowNum()+" Column: "+s.getColNum());
			}}
			
				break;
			}
			case 3:{
				String booking =tickets.reserveSeats(seats.getSeatHoldId(), email);
				if(booking!=null){
				System.out.println("Your seats are booked.");
				System.out.println("Confirmation Code: " + booking);
				System.exit(0);
				}
				else{
					System.out.println("Please hold the seats first.");
				}	

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
