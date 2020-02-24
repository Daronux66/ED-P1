package ule.edi.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import ule.edi.model.*;
import ule.edi.model.Configuration.Type;


public class EventArrayImpl implements Event {

	private String name;
	private Date eventDate;
	private int nSeats;

	private Double price;    // precio de entradas 
	private Byte discountAdvanceSale;   // descuento en venta anticipada (0..100)

	private Seat[] seats;



	public EventArrayImpl(String name, Date date, int nSeats){
		//TODO 
		// utiliza los precios por defecto: DEFAULT_PRICE y DEFAULT_DISCOUNT definidos en Configuration.java   
		// Debe crear el array de butacas
		this.name=name;
		this.eventDate=date;
		this.seats= new Seat[nSeats];
		this.price=Configuration.DEFAULT_PRICE;
		this.discountAdvanceSale=Configuration.DEFAULT_DISCOUNT;
	}


	public EventArrayImpl(String name, Date date, int nSeats, Double price, Byte discount){
		//TODO 
		// Debe crear el array de butacas
		this.name=name;
		this.eventDate=date;
		this.seats= new Seat[nSeats];
		this.price=price;
		this.discountAdvanceSale=discount;
	}

//.
	@Override
	public String getName() {
		return this.name;
	}

//?
	@Override
	public Date getDateEvent() {
		return this.eventDate;
	}


	@Override
	public Double getPrice() {
		return this.price;
	}


	@Override
	public Byte getDiscountAdvanceSale() {
		return this.discountAdvanceSale;
	}


	@Override
	public int getNumberOfSoldSeats() {
		return (this.getNumberOfSeats()-this.getNumberOfAvailableSeats());
	}


	@Override
	public int getNumberOfNormalSaleSeats() {
		int normalCounter=0;
		for (int i=0; i<this.nSeats; i++) {
			if (this.seats[i]==null) {
			}
			else if(isAdvanceSale(seats[i].getHolder())) {
			}
			else {
				normalCounter++;
			}
		}
		return normalCounter;
	}


	@Override
	public int getNumberOfAdvanceSaleSeats() {
		return (this.getNumberOfSoldSeats()-this.getNumberOfNormalSaleSeats());
	}


	@Override
	public int getNumberOfSeats() {
		return this.nSeats;
	}

//
	@Override
	public int getNumberOfAvailableSeats() {
		int availableCounter=0;
		for (int i=0;i<this.nSeats; i++) {
			if(seats[i]==null) {
				availableCounter++;
			}
		}
		return availableCounter;
	}


	@Override
	public Seat getSeat(int pos) {
		return this.seats[pos-1];
	}


	@Override
	public Person refundSeat(int pos) {
		Person p;
		if(seats[pos-1]==null) {
			return null;
		}
		else {
			p=this.seats[pos-1].getHolder();
			seats[pos-1]=null;
			return p;
		}
	}


	@Override
	public boolean sellSeat(int pos, Person p, boolean advanceSale) {
		boolean free=false;
		if (!(this.seats[pos-1]== null)) {
			free=true;
			Type type;
			if(advanceSale) {
				type=Type.ADVANCE_SALE;
			}
			else {
				type=Type.NORMAL;
			}
			this.seats[pos-1]= new Seat(this , pos-1, type, p);
		}
		return free;
	}


	@Override
	public int getNumberOfAttendingChildren() {
		int childCounter=0;
		for (int i=0; i<nSeats; i++) {
			if (!(seats[i]==null)) {
				if(seats[i].getHolder().getAge()<Configuration.CHILDREN_EXMAX_AGE) {
					childCounter++;
				}
			}
		}
		return childCounter;
	}


	@Override
	public int getNumberOfAttendingAdults() {
		int adultCounter=0;
		for (int i=0; i<nSeats; i++) {
			if (!(seats[i]==null)) {
				if(seats[i].getHolder().getAge()>=Configuration.CHILDREN_EXMAX_AGE && 
						seats[i].getHolder().getAge()<Configuration.ELDERLY_PERSON_INMIN_AGE) {
					adultCounter++;
				}
			}
		}
		return adultCounter;
	}


	@Override
	public int getNumberOfAttendingElderlyPeople() {
		int elderyCounter=0;
		for (int i=0; i<nSeats; i++) {
			if (!(seats[i]==null)) {
				if(seats[i].getHolder().getAge()>=Configuration.ELDERLY_PERSON_INMIN_AGE) {
					elderyCounter++;
				}
			}
		}
		return elderyCounter;
	}


	@Override
	public List<Integer> getAvailableSeatsList() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<nSeats; i++) {
			if(this.seats[i]==null) {
				list.add(i+1);
			}
		}
		return list;
	}


	@Override
	public List<Integer> getAdvanceSaleSeatsList() {
		List<Integer> list = new ArrayList<Integer>();
		Type advanceSale= Type.ADVANCE_SALE;
		for (int i=0; i<nSeats; i++) {
			if(!(this.seats[i]==null)) {
				if(this.seats[i].getType()==advanceSale)
					list.add(i+1);
			}
		}
		return list;
	}


	@Override
	public int getMaxNumberConsecutiveSeats() {
		int consecutiveCounter=0;
		int maxConsecutive=0;
		for(int i=0; i<nSeats; i++) {
			if(this.seats[i]==null) {
				consecutiveCounter++;
			}
			else {
				if(consecutiveCounter>maxConsecutive) {
					maxConsecutive=consecutiveCounter;
				}
				consecutiveCounter=0;
			}
		}
		return maxConsecutive;
	}


	@Override
	public Double getPrice(Seat seat) {
		Type advaceSale= Type.ADVANCE_SALE;
		if(seat==null) {
			return 0.0;
		}
		else {
			if(seat.getType()==advaceSale) {
				this.price=getPrice()-Configuration.DEFAULT_DISCOUNT;
			}
			return this.getPrice();
		}
	}


	@Override
	public Double getCollectionEvent() {
		double collection=0.0;
		for(int i=0; i<nSeats; i++) {
			if(this.seats[i]!=null) {
				collection+= this.getPrice(this.seats[i]);
			}
		}
		return collection;
	}


	@Override
	public int getPosPerson(Person p) {
		return 0;
	}


	@Override
	public boolean isAdvanceSale(Person p) {
		boolean sale=false;
		//revisar
		return sale;
	}

}	