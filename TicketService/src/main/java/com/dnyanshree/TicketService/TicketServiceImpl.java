package com.dnyanshree.TicketService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TicketServiceImpl implements ITicketService {
	private Map<VenueLevel, ArrayList<Seat>> tickets = new HashMap<VenueLevel, ArrayList<Seat>>();
	private ArrayList<Seat> level1Seats = new ArrayList<Seat>();
	private ArrayList<Seat> level2Seats = new ArrayList<Seat>();
	private ArrayList<Seat> level3Seats = new ArrayList<Seat>();
	private ArrayList<Seat> level4Seats = new ArrayList<Seat>();
	private ArrayList<SeatHold> seatHoldList = new ArrayList<>();
	private int id=5465;
	//private int id=(int) (System.currentTimeMillis() & 0xfffffff);//generate random number for hold id
	private Timer timer;
	
	public void init() {
		for (int i = 0; i < VenueLevel.ORCHESTRA.getMaxRows(); i++) {
			for (int j = 0; j < VenueLevel.ORCHESTRA.getMaxCols(); j++)
				level1Seats.add(new Seat(i, j, false, false));
		}
		// System.out.println(level1Seats.size());
		for (int i = 0; i < VenueLevel.MAIN.getMaxRows(); i++) {
			for (int j = 0; j < VenueLevel.MAIN.getMaxCols(); j++)
				level2Seats.add(new Seat(i, j, false, false));
		}
		for (int i = 0; i < VenueLevel.BALCONY1.getMaxRows(); i++) {
			for (int j = 0; j < VenueLevel.BALCONY1.getMaxCols(); j++)
				level3Seats.add(new Seat(i, j, false, false));
		}
		for (int i = 0; i < VenueLevel.BALCONY2.getMaxRows(); i++) {
			for (int j = 0; j < VenueLevel.BALCONY2.getMaxCols(); j++)
				level4Seats.add(new Seat(i, j, false, false));
		}
		tickets.put(VenueLevel.ORCHESTRA, level1Seats);
		tickets.put(VenueLevel.MAIN, level2Seats);
		tickets.put(VenueLevel.BALCONY1, level3Seats);
		tickets.put(VenueLevel.BALCONY2, level4Seats);
	}

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		int numOfSeats = 0;
		if (venueLevel.isPresent()) {
			if (venueLevel.equals(VenueLevel.ORCHESTRA.getValue())) {
				return countAvailSeatsInList(level1Seats);
			} else if (venueLevel.equals(VenueLevel.MAIN.getValue())) {
				return countAvailSeatsInList(level2Seats);
			} else if (venueLevel.equals(VenueLevel.BALCONY1.getValue())) {
				return countAvailSeatsInList(level3Seats);
			} else if (venueLevel.equals(VenueLevel.BALCONY2.getValue())) {
				return countAvailSeatsInList(level4Seats);
			}
		} else { // return all available seats for all levels
			numOfSeats = countAvailSeatsInList(level1Seats) + countAvailSeatsInList(level2Seats)
					+ countAvailSeatsInList(level3Seats) + countAvailSeatsInList(level4Seats);
			return numOfSeats;
		}
		return numOfSeats;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		// arraylist to hold the seats
		ArrayList<Seat> seats = new ArrayList<Seat>(numSeats);
		// seathold object that will be returned containing arraylist of seats and other details
		SeatHold seatHoldObj = new SeatHold();
		// convert minlevel and maxt level to int
		// set default value for maxlevel as 4
		int maxLevelInt = maxLevel.map(Integer::intValue).orElse(4);
		// set default value for minlevel as 1
		int minLevelInt = minLevel.map(Integer::intValue).orElse(1);
		if (validateFindAndHoldSeatsParameters(numSeats, minLevelInt, maxLevelInt, customerEmail)) {
			// find & hold best seats
			// iterate from maxlevel to minlevel
			for (int i = maxLevelInt; i >= minLevelInt;) {
				int numOfAvailSeatsInLevel = numSeatsAvailable(Optional.ofNullable(i));
				// check if max avail seats at that level is greater than equal to required seats
				if (numOfAvailSeatsInLevel >= numSeats) {
					int selectedRow = 0;
					switch (i) {
					case 1: {
						int maxColsForLevel = getMaxColsForLevel(i);
						selectedRow = findRowForHold(level1Seats, numSeats);
						seats = findBestSeatsForHold(level1Seats, selectedRow, numSeats, maxColsForLevel);
						break;
					}
					case 2: {
						int maxColsForLevel = getMaxColsForLevel(i);
						selectedRow = findRowForHold(level2Seats, numSeats);
						seats = findBestSeatsForHold(level2Seats, selectedRow, numSeats, maxColsForLevel);
						break;
					}
					case 3: {
						int maxColsForLevel = getMaxColsForLevel(i);
						selectedRow = findRowForHold(level3Seats, numSeats);
						seats = findBestSeatsForHold(level3Seats, selectedRow, numSeats, maxColsForLevel);
						break;
					}
					case 4: {
						int maxColsForLevel = getMaxColsForLevel(i);
						selectedRow = findRowForHold(level4Seats, numSeats);
						seats = findBestSeatsForHold(level4Seats, selectedRow, numSeats, maxColsForLevel);
						break;
					}
					default:{
						System.out.println("Seats not available at level: " + i);
						break;
					}
					}
				}
				for (Seat seat : seats) {
					seatHoldObj.SeatList.add(seat);
				}
				
				seatHoldObj.setSeatHoldId(id);		
				seatHoldObj.setCustomerEmail(customerEmail);
				seatHoldObj.setLevelNum(i);
				seatHoldList.add(seatHoldObj);
				startTimer(60,id,i);
				id+=1;
				//seatHoldList.forEach(System.out::println);
				return seatHoldObj;
			}
		} else{
			System.out.println("No seats booked. Try again!");}
		return null;
	}

	private ArrayList<Seat> findBestSeatsForHold(ArrayList<Seat> list, int selectedRow, int numSeats, int maxCols) {
		ArrayList<Seat> seats = new ArrayList<Seat>(numSeats);
		int startCol = 0;
		int endCol = 0;
		for (Seat s : list) {
			if (s.getRowNum() == selectedRow) {
				if (s.getColNum() == (maxCols / 2) && s.isHold() == false && s.isReserved() == false) {
					startCol = maxCols / 2 - numSeats / 2;
					endCol = maxCols / 2 + (numSeats - numSeats / 2);
				} else if (s.getColNum() == 0 && s.isHold() == false && s.isReserved() == false) {
					startCol = 0;
					endCol = numSeats;
				} else if (s.getColNum() == maxCols && s.isHold() == false && s.isReserved() == false) {
					startCol = maxCols - numSeats - 1;
					endCol = maxCols - 1;
				} else {
					for (int col = numSeats; col < maxCols - numSeats; col++) {
						if (s.getColNum() == col && s.isHold() == false && s.isReserved() == false) {
							startCol = col;
							endCol = col + numSeats;
							break;
						}
					}
				}
				for (int j = startCol; j < endCol; j++) {
					list.get(j).setHold(true);
					seats.add(new Seat(list.get(j).getRowNum(), list.get(j).getColNum(), false, true));
				}
				// seats.forEach(System.out::println);
				return seats;
			}
		}
		return seats;
	}

	private int findRowForHold(ArrayList<Seat> list, int numSeats) {
		list.sort(Comparator.comparing(Seat::getRowNum).reversed());
		Map<Integer, Long> counted = list.stream().filter(p -> p.isReserved() == false).filter(p -> p.isHold() == false)
				.collect(Collectors.groupingBy(Seat::getRowNum, Collectors.counting()));
		counted.forEach((k, v) -> {
			if (v.intValue() < numSeats)
				counted.remove(k);
		});
		Optional<Integer> max = counted.keySet().stream().max(Integer::compareTo);
		if (max.isPresent()) {
			return max.get().intValue();
		} else {
			System.out.println("No row present with " + numSeats + " vacant seats in this level");
			return 0;
		}
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		//access seatholdlist to find seats with given seathold id and email
		for (SeatHold seatHold : seatHoldList) {
			if (seatHold.getSeatHoldId() == seatHoldId && seatHold.getCustomerEmail().equals(customerEmail)) {
				for (Seat seat : seatHold.SeatList) {
						seat.setHold(false);
						seat.setReserved(true);
					} // also update actual arraylist
					switch(seatHold.getLevelNum()){
					case 1:{
						updateListForReserve(seatHold.SeatList, level1Seats);
						break;
					}
					case 2:{
						updateListForReserve(seatHold.SeatList, level2Seats);
						break;
					}
					case 3:{
						updateListForReserve(seatHold.SeatList, level3Seats);
						break;
					}
					case 4:{
						updateListForReserve(seatHold.SeatList, level4Seats);
						break;
					}
					}
					timer.cancel();
						seatHold.setConfirmCode(String.valueOf(seatHold.getSeatHoldId()));
					return seatHold.getConfirmCode();
			} else {// user entered invalid holdId &/or email
				System.out.println("Enter valid details. Try Again!");
			}
		}
		return null;
	}

	private int countAvailSeatsInList(Collection<Seat> list) {
		Map<Seat, Long> counted = list.stream().filter(p -> p.isReserved() == false).filter(p -> p.isHold() == false)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return counted.values().size();

	}

	private boolean validateFindAndHoldSeatsParameters(int numSeats, int minLevel, int maxLevel, String customerEmail) {
		if (validateNumOfSeatsRequested(numSeats))
			if(validateLevels(minLevel, maxLevel)) 
				if(validateEmail(customerEmail))
			return true;
		return false;
	}

	private boolean validateEmail(String customerEmail) {
		if (customerEmail.contains("@") && customerEmail.contains("."))
			return true;
		else
			System.out.println("please enter a valid email id.");
		return false;
	}

	private boolean validateNumOfSeatsRequested(int numSeats) {
		if (numSeats > 0 && numSeats <= 5)
			return true;
		else
			System.out.println("Number of seats should be between 1 and 5");
		return false;
	}

	private boolean validateLevels(int minLevel, int maxLevel) {
		if (minLevel > maxLevel) {
			System.out.println("maxlevel = " + maxLevel);
			System.out.println("minlevel = " + minLevel);
			System.out.println("Max level cannot be less than Min Level. Cannot hold seats.");
			
			return false;
		} else
			return true;

	}

	private int getMaxColsForLevel(int level) {
		int maxCols = 0;
		Optional<Integer> levelNum = Optional.ofNullable(level);
		if (levelNum.equals(VenueLevel.ORCHESTRA.getValue()))
			maxCols = VenueLevel.ORCHESTRA.getMaxCols();
		else if (levelNum.equals(VenueLevel.MAIN.getValue()))
			maxCols = VenueLevel.MAIN.getMaxCols();
		else if (levelNum.equals(VenueLevel.BALCONY1.getValue()))
			maxCols = VenueLevel.BALCONY1.getMaxCols();
		else if (levelNum.equals(VenueLevel.BALCONY2.getValue()))
			maxCols = VenueLevel.BALCONY2.getMaxCols();

		return maxCols;
	}

	private void startTimer(int seconds, int holdId, int level){
		timer = new Timer();
		timer.schedule( new removeHold(holdId,level), seconds * 1000);		
	}
	
	class removeHold extends TimerTask{	
		private int holdId,level;
		public removeHold(int holdId,int level) {
			this.holdId=holdId;
			this.level=level;
		}

		@Override
		public void run() {			
			  timer.cancel();
			  System.out.println("Timeout! Start over again.");
			  removeHoldformList(holdId,level);			
		}		
	}
	
	public void removeHoldformList(int holdId,int level){
		ArrayList<Seat> seatsToRemoveHold=new ArrayList<Seat>();			
		for(Iterator<SeatHold> itr = seatHoldList.iterator(); itr.hasNext();){
				SeatHold seat = itr.next();
			if(seat.getSeatHoldId() == holdId){
				seatsToRemoveHold.addAll(seat.SeatList);
				itr.remove();
				}
			}
		//seatsToRemove.forEach(System.out::println);;
		switch(level){
		case 1:{
			updateListForHold(seatsToRemoveHold, level1Seats);				
			break;
		}
		case 2:{
			updateListForHold(seatsToRemoveHold, level2Seats);
			break;
		}
		case 3:{
			updateListForHold(seatsToRemoveHold, level3Seats);
			break;
		}
		case 4:{
			updateListForHold(seatsToRemoveHold, level4Seats);
			break;
		}
		}
		System.out.println("HoldID : "+holdId+ " is no longer valid for reservation. Try Again!");
	}
	
	public void updateListForHold(ArrayList<Seat> seatsToRemoveHold, ArrayList<Seat> listToUpDate){
		for (Seat seatToUpdate : listToUpDate) {
			for (Seat seat : seatsToRemoveHold) {
				if(seatToUpdate.getRowNum()==seat.getRowNum() && 
						seatToUpdate.getColNum()==seat.getColNum()){
					seatToUpdate.setHold(false);
				}
			}				
		}
		
	}
	public void updateListForReserve(ArrayList<Seat> seatsToReserve, ArrayList<Seat> listToUpDate){
		for (Seat seatToUpdate : listToUpDate) {
			for (Seat seat : seatsToReserve) {
				if(seatToUpdate.getRowNum()==seat.getRowNum() && 
						seatToUpdate.getColNum()==seat.getColNum()){
					seatToUpdate.setHold(false);
					seatToUpdate.setReserved(true);
					//System.out.println("Done");
				}
			}				
		}		
	}
	public void clearAllInits() {
		level1Seats.clear();
		level2Seats.clear();
		level3Seats.clear();
		level4Seats.clear();
		seatHoldList.clear();
	}

}
