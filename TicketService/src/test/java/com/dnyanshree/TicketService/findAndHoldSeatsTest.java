package com.dnyanshree.TicketService;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

public class findAndHoldSeatsTest {

	@Test
	public void testFindAndHoldSeats() {
		TicketServiceImpl tickets = new TicketServiceImpl(); 
		tickets.init();   			
		SeatHold actual = tickets.findAndHoldSeats(2, Optional.ofNullable(1), Optional.ofNullable(4), "a@c.com");
		String expected ="SeatHold [seatHoldId=5000, customerEmail=a@c.com, confirmCode=null, levelNum=4, SeatList=[Seat [rowNum=14, colNum=0, isReserved=false, isHold=true], Seat [rowNum=14, colNum=1, isReserved=false, isHold=true]]]";
		assertEquals(expected, actual.toString());	
	}

}
