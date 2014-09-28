package ru.psavinov.lib.format.writer;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.exception.UnsupportedEntityException;
import ru.psavinov.lib.format.object.Car;
import ru.psavinov.lib.format.writer.XMLWriter;

public class XMLWriterTest {
	
	class BadCar extends Car {
		private URL unsupportedProperty;
		
		public URL getURL(){
			return this.unsupportedProperty;
		}
		
		public void setURL(URL u){
			this.unsupportedProperty = u;
		}
	}
	
	@Test
	public void testWriteObjects() throws Exception {
		XMLWriter w = new XMLWriter(System.out);
		List<IObject> list = new ArrayList<IObject>();
		Car car = new Car();
		car.setBrandName("Ford");
		car.setDate(new Date());
		car.setPrice(123456789);
		list.add(car);
		w.write(list, Car.class);
	}
	
	@Test
	public void testIncorrectObject() throws Exception {
		XMLWriter w = new XMLWriter(System.out);
		List<IObject> list = new ArrayList<IObject>();
		BadCar car = new BadCar();
		car.setBrandName("Lada");
		car.setDate(new Date());
		car.setPrice(987654321);
		car.setURL(new URL("http://www.lada.ru"));
		list.add(car);
		try {
			w.write(list, BadCar.class);
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedEntityException);
		}
	}
}
