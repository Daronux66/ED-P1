package ule.edi.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.*;

import ule.edi.model.*;

public class EventArrayImplTests {

	private DateFormat dformat = null;
	private EventArrayImpl e;
	
	private Date parseLocalDate(String spec) throws ParseException {
        return dformat.parse(spec);
	}

	public EventArrayImplTests() {
		
		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Before
	public void testBefore() throws Exception{
	    e = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 110);

	}
	
	@Test
	public void testEventoVacio() throws Exception {
	    Assert.assertTrue(e.getNumberOfAvailableSeats()==110);
	    Assert.assertEquals(e.getNumberOfAvailableSeats(), 110);
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
	}
	
	@Test
	public void testSellSeat1Adult() throws Exception{
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
		Assert.assertTrue(e.sellSeat(1, new Person("10203040A","Alice", 34),false));	//venta normal
		Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);  
		Assert.assertEquals(e.getNumberOfNormalSaleSeats(), 1);
	  
	}
	

	
	@Test
	public void testgetCollection() throws Exception{
		Event  ep = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		Assert.assertEquals(ep.sellSeat(1, new Person("1010", "AA", 10), true),true);
		Assert.assertTrue(ep.getCollectionEvent()==75);					
	}
	
	// TODO EL RESTO DE MÉTODOS DE TESTS NECESARIOS PARA LA COMPLETA COMPROBACIÓN DEL BUEN FUNCIONAMIENTO DE TODO EL CÓDIGO IMPLEMENTADO
	
	@Test
	public void testEventArrayImplDefault() {
		Assert.assertEquals("The Fabulous Five", e.getName());
		Assert.assertEquals("Sat Feb 24 17:00:00 CET 2018", e.getDateEvent().toString());
		Assert.assertEquals(110, e.getNumberOfSeats());
		Assert.assertEquals(Configuration.DEFAULT_PRICE, e.getPrice());
		Assert.assertEquals(Configuration.DEFAULT_DISCOUNT, e.getDiscountAdvanceSale());
	}
	
	@Test
	public void testEventArrayImplPerso() throws ParseException {
		EventArrayImpl q = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 110, 120.5, (byte) 10);
		Assert.assertEquals("The Fabulous Five", q.getName());
		Assert.assertEquals("Sat Feb 24 17:00:00 CET 2018", q.getDateEvent().toString());
		Assert.assertEquals(110, q.getNumberOfSeats());
		Assert.assertTrue(q.getPrice()==120.5);
		Assert.assertTrue(q.getDiscountAdvanceSale()==10);
	}
	
	@Test
	public void testGetName() {
		Assert.assertEquals("The Fabulous Five", e.getName());
	}
	
	@Test
	public void testGetDateEvent() {
		Assert.assertEquals("Sat Feb 24 17:00:00 CET 2018", e.getDateEvent().toString());
	}
	
	@Test
	public void testGetPrice() {
		Assert.assertEquals(Configuration.DEFAULT_PRICE, e.getPrice());
	}
	
	@Test
	public void testGetDiscountAdvanceSale() {
		Assert.assertEquals(Configuration.DEFAULT_DISCOUNT, e.getDiscountAdvanceSale());
	}
	
	@Test
	public void testGetNumberOfSoldSeats() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("10203040A","Alice", 34), false);
		Assert.assertEquals(2, e.getNumberOfSoldSeats());
	}
	
	@Test
	public void testGetNumberOfNormalSaleSeats() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), false);
		e.sellSeat(3, new Person("10203040A","Alice", 34), false);
		e.sellSeat(7, new Person("11111040A","Rober", 20), true);
		e.sellSeat(6, new Person("22222040A","Kupina", 40), false);
		Assert.assertEquals(3, e.getNumberOfNormalSaleSeats());
	}
	
	@Test
	public void testGetNumberOfAdvanceSaleSeats() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("10203040A","Alice", 34), true);
		e.sellSeat(7, new Person("11111040A","Rober", 20), true);
		e.sellSeat(6, new Person("22222040A","Kupina", 40), false);
		Assert.assertEquals(3, e.getNumberOfAdvanceSaleSeats());
	}
	
	@Test
	public void testGetNumberOfSeats() {
		Assert.assertEquals(110, e.getNumberOfSeats());
	}
	
	@Test
	public void testRefundSeat() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("10203040A","Alice", 34), false);
		e.sellSeat(7, new Person("11111040A","Rober", 20), true);
		Person kupina = new Person("22222040A","Kupina", 40);
		e.sellSeat(6, kupina, false);
		Assert.assertEquals(kupina, e.refundSeat(6));
	}
	
	@Test
	public void testDontRefundSeat() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("10203040A","Alice", 34), false);
		e.sellSeat(7, new Person("11111040A","Rober", 20), true);
		Assert.assertEquals(null, e.refundSeat(6));
	}
	
	@Test
	public void testSellSeat() {
		Assert.assertTrue(e.sellSeat(3, new Person("10203040A","Alice", 34), false));
	}
	
	@Test
	public void testDontSellSeat() {
		e.sellSeat(3, new Person("10203040A","Alice", 34), false);
		Assert.assertFalse(e.sellSeat(3, new Person("22222040A","Kupina", 40), false));
	}
	
	@Test
	public void testGetNumberOfAttendingChildren() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("10203040A","Alice", 34), true);
		e.sellSeat(7, new Person("11111040A","Rober", 13), true);
		e.sellSeat(6, new Person("22222040A","Kupina", 14), false);
		Assert.assertEquals(1, e.getNumberOfAttendingChildren());
	}
	
	@Test
	public void testGetNumberOfAttendingAdults() {
		e.sellSeat(1, new Person("10203040A","Alice", 64), true);
		e.sellSeat(3, new Person("33333040A","Potito", 65), true);
		e.sellSeat(7, new Person("11111040A","Rober", 13), true);
		e.sellSeat(6, new Person("22222040A","Kupina", 14), false);
		Assert.assertEquals(2, e.getNumberOfAttendingAdults());
	}
	
	@Test
	public void testGetNumberOfAttendingElderlyPeople() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("33333040A","Potito", 65), true);
		e.sellSeat(7, new Person("11111040A","Rober", 68), true);
		e.sellSeat(6, new Person("22222040A","Kupina", 14), false);
		Assert.assertEquals(2, e.getNumberOfAttendingElderlyPeople());
	}
	
	@Test
	public void testGetAvailableSeatsList() {
		Person kupina = new Person("22222040A","Kupina", 14);
		for (int i=3; i<=e.getNumberOfSeats(); i++) {
			e.sellSeat(i, kupina, true);
		}
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		Assert.assertEquals(list, e.getAvailableSeatsList());
	}
	
	@Test
	public void testGetAdvanceSaleSeatsList() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("33333040A","Potito", 65), true);
		e.sellSeat(7, new Person("11111040A","Rober", 68), true);
		e.sellSeat(6, new Person("22222040A","Kupina", 14), false);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		list.add(7);
		Assert.assertEquals(list, e.getAdvanceSaleSeatsList());
	}
	
	@Test
	public void testGetMaxNumberConsecutiveSeats() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("33333040A","Potito", 65), true);
		e.sellSeat(20, new Person("11111040A","Rober", 68), true);
		Person kupina = new Person("22222040A","Kupina", 14);
		for (int i=21; i<=105; i++) {
			e.sellSeat(i, kupina, false);
		}
		Assert.assertEquals(16, e.getMaxNumberConsecutiveSeats());
	}
	
	@Test
	public void testGetFreeSeatPrice() {
		Assert.assertTrue(e.getPrice(e.getSeat(1))==0.0);
	}
	
	@Test
	public void testGetNormalSeatPrice() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), false);
		Assert.assertTrue(e.getPrice(e.getSeat(1))==100.0);
	}
	
	@Test
	public void testGetAdvancedSaleSeatPrice() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		double price=(100.0-25);
		Assert.assertTrue(e.getPrice(e.getSeat(1))==price);
	}
	
	@Test
	public void testGetCollectionEvent() {
		e.sellSeat(1, new Person("10203040A","Alice", 34), true);
		e.sellSeat(3, new Person("33333040A","Potito", 65), true);
		e.sellSeat(20, new Person("11111040A","Rober", 68), false);
		Assert.assertTrue(e.getCollectionEvent()==250.0);
	}
	
	@Test
	public void testGetPosPerson() {
		Person alice=new Person("10203040A","Alice", 34);
		e.sellSeat(1, new Person("33333040A","Potito", 65), true);
		e.sellSeat(4, alice, false);
		e.sellSeat(5, alice, true);
		Assert.assertEquals(4,e.getPosPerson(alice));
	}
	
	@Test
	public void testGetNotPersonFound() {
		Person alice=new Person("10203040A","Alice", 34);
		e.sellSeat(1, new Person("33333040A","Potito", 65), true);
		Assert.assertEquals(-1, e.getPosPerson(alice));
	}
	
	@Test
	public void testIsAdvanceSale () {
		Person alice=new Person("10203040A","Alice", 34);
		e.sellSeat(1, alice, true);
		e.sellSeat(3, alice, false);
		e.sellSeat(20, new Person("11111040A","Rober", 68), false);
		Assert.assertTrue(e.isAdvanceSale(alice));
	}
	
	@Test
	public void testIsNotAdvanceSale() {
		Person alice=new Person("10203040A","Alice", 34);
		e.sellSeat(1, alice, false);
		e.sellSeat(3, alice, true);
		e.sellSeat(20, new Person("11111040A","Rober", 68), false);
		Assert.assertFalse(e.isAdvanceSale(alice));
	}
	
	@Test
	public void testEqualsPerson() {
		Person kupina = new Person("22222040A","Kupina", 14);
		Person kupinaClone = new Person("22222040A","Kupina", 14);
		Assert.assertEquals(true, kupina.equals(kupinaClone));

	}
	
	@Test
	public void testNotEqualsPerson() {
		Person kupina = new Person("22222040A","Kupina", 14);
		Person potito = new Person("33333040A","Potito", 65);
		Assert.assertEquals(false, kupina.equals(potito));

	}
}
