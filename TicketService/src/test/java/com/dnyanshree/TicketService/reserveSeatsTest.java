package com.dnyanshree.TicketService;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

public class reserveSeatsTest {

	@Test
	public void testReserveSeats() {
		TicketServiceImpl tickets = new TicketServiceImpl(); 
		tickets.init();   			
		tickets.findAndHoldSeats(2, Optional.ofNullable(1), Optional.ofNullable(4), "a@c.com");
		String actual =tickets.reserveSeats(5000, "a@c.com");
		String expected = String.valueOf(5000);
				assertEquals(expected, actual);

	}

}
