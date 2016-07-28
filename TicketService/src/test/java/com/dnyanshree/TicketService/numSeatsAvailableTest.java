package com.dnyanshree.TicketService;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

public class numSeatsAvailableTest {

	@Test
	public void testNumSeatsAvailable() {
		TicketServiceImpl tickets = new TicketServiceImpl(); 
				tickets.init();   			
		int result = tickets.numSeatsAvailable(Optional.ofNullable(3));
		assertEquals(1500, result);		
	}

}
