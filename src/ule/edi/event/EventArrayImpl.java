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
		this.nSeats=nSeats;
		this.seats= new Seat[nSeats];
		this.price=Configuration.DEFAULT_PRICE;
		this.discountAdvanceSale=Configuration.DEFAULT_DISCOUNT;
	}


	public EventArrayImpl(String name, Date date, int nSeats, Double price, Byte discount){
		//TODO 
		// Debe crear el array de butacas
		this.name=name;
		this.eventDate=date;
		this.nSeats=nSeats;
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
		for (int i=0; i<this.seats.length; i++) {
			if (this.seats[i]!=null) {
				if(this.seats[i].getType()==Type.NORMAL){
					normalCounter++;
				}
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

	@Override
	public int getNumberOfAvailableSeats() {
		int availableCounter=0;
		for (int i=0;i<this.seats.length; i++) {
			if(this.seats[i]==null) {
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
		int arraypos=(pos-1);
		if (this.seats[arraypos]== null) {
			free=true;
			if(advanceSale) {
				this.seats[arraypos]= new Seat(this , pos, Type.ADVANCE_SALE, p);
			}
			else {
				this.seats[arraypos]= new Seat(this , pos, Type.NORMAL, p);
			}
		}
		return free;
	}


	@Override
	public int getNumberOfAttendingChildren() {
		int childCounter=0;
		for (int i=0; i<this.seats.length; i++) {
			if (seats[i]!=null) {
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
		for (int i=0; i<this.seats.length; i++) {
			if (this.seats[i]!=null) {
				if((this.seats[i].getHolder().getAge())>=Configuration.CHILDREN_EXMAX_AGE && 
						(this.seats[i].getHolder().getAge())<Configuration.ELDERLY_PERSON_INMIN_AGE) {
					adultCounter++;
				}
			}
		}
		return adultCounter;
	}


	@Override
	public int getNumberOfAttendingElderlyPeople() {
		int elderyCounter=0;
		for (int i=0; i<this.seats.length; i++) {
			if (seats[i]!=null) {
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
		for (int i=0; i<this.seats.length; i++) {
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
		for (int i=0; i<this.seats.length; i++) {
			if(this.seats[i]!=null) {
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
		for(int i=0; i<this.seats.length; i++) {
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
		Double price=this.price;
		if(seat==null) {
			return 0.0;
		}
		else {
			if(seat.getType()==advaceSale) {
				price=this.getPrice()-this.discountAdvanceSale;
			}
			return price;
		}
	}


	@Override
	public Double getCollectionEvent() {
		double collection=0.0;
		for(int i=0; i<this.seats.length; i++) {
			if(this.seats[i]!=null) {
				collection+= this.getPrice(this.seats[i]);
			}
		}
		return collection;
	}


	@Override
	public int getPosPerson(Person p) {
		int posP=-1;
		for(int i=0; i<this.seats.length; i++) {
			if(this.seats[i]!=null) {
				if(this.seats[i].getHolder().equals(p)) {
					posP = i+1;
					i=this.seats.length;	//Acabar con el for porque solo pilla la primera persona que coincida
				}
			}
		}
		return posP;
	}


	@Override
	public boolean isAdvanceSale(Person p) {
		boolean advancedSale=false;
		int posP = this.getPosPerson(p);
		Type type = this.getSeat(posP).getType();
		if (type== Type.ADVANCE_SALE) {
			advancedSale=true;
		}
		return advancedSale;
	}

}	