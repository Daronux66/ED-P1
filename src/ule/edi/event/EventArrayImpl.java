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
			//this.seats[pos-1]= new Seat(Event.super., pos-1, type, p);
		}
		return free;
	}


	@Override
	public int getNumberOfAttendingChildren() {
		return 0;
	}


	@Override
	public int getNumberOfAttendingAdults() {
		return 0;
	}


	@Override
	public int getNumberOfAttendingElderlyPeople() {
		return 0;
	}


	@Override
	public List<Integer> getAvailableSeatsList() {
		return null;
	}


	@Override
	public List<Integer> getAdvanceSaleSeatsList() {
		return null;
	}


	@Override
	public int getMaxNumberConsecutiveSeats() {
		return 0;
	}


	@Override
	public Double getPrice(Seat seat) {
		if(seat==null) {
			return 0.0;
		}
		else {
			return this.getPrice();
		}
	}


	@Override
	public Double getCollectionEvent() {
		return null;
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