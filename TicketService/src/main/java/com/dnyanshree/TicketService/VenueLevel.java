package com.dnyanshree.TicketService;

import java.util.Optional;

public enum VenueLevel {
	
	ORCHESTRA(1,25,50), MAIN(2,20,100), BALCONY1(3,15,100), BALCONY2(4,15,100); 
	
	private int maxRows;
	private int maxCols;	
	private int value;

	VenueLevel(int value,int maxRows,int maxCols){
		this.value = value;
		this.maxRows = maxRows;
		this.maxCols = maxCols;
	}
	public Optional<Integer> getValue() {
		Optional<Integer> level = Optional.ofNullable(value);		
		return level;
	}
	public int getMaxRows() {
		return maxRows;
	}
	public int getMaxCols() {
		return maxCols;
	}
}
