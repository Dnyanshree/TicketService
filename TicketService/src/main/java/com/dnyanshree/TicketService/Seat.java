package com.dnyanshree.TicketService;

public class Seat{
	
	private int rowNum;
	private int colNum;
	private boolean isReserved;
	private boolean isHold;
	
	public Seat(int rowNum, int colNum, boolean isReserved, boolean isHold) {
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.isReserved = isReserved;
		this.isHold = isHold;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	public boolean isReserved() {
		return isReserved;
	}
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	public boolean isHold() {
		return isHold;
	}
	public void setHold(boolean isHold) {
		this.isHold = isHold;
	}
	
	@Override
	public String toString() {
		return "Seat [rowNum=" + rowNum + ", colNum=" + colNum + ", isReserved=" + isReserved + ", isHold=" + isHold
				+ "]";
	}

}
