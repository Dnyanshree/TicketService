package com.dnyanshree.TicketService;

import java.util.ArrayList;

public class SeatHold {
	
	private int seatHoldId;
	private String customerEmail;	
	private String confirmCode;
	private int levelNum;
	ArrayList<Seat> SeatList = new ArrayList<Seat>();	
	
	public int getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getConfirmCode() {
		return confirmCode;
	}
	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}
	public int getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	@Override
	public String toString() {
		return "SeatHold [seatHoldId=" + seatHoldId + ", customerEmail=" + customerEmail + ", confirmCode="
				+ confirmCode + ", levelNum=" + levelNum + ", SeatList=" + SeatList + "]";
	}

}
