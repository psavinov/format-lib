package ru.psavinov.lib.format.writer;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.IWriter;
import ru.psavinov.lib.format.exception.UnsupportedEntityException;
import ru.psavinov.lib.format.object.Car;
import ru.psavinov.lib.format.writer.BinaryWriter;

public class BinaryWriterTest {
	
	class BadCar extends Car {
		@SuppressWarnings("unused")
		private URL url;
	}

	@Test
	public void testWriteObject() throws Exception {
		Car car = new Car();
		car.setBrandName("Ford");
		car.setDate(new Date());
		car.setPrice(123456789);
		List<IObject> writeList = new ArrayList<IObject>();
		writeList.add(car);
		IWriter w = new BinaryWriter(System.out);
		w.write(writeList, Car.class);
	}
	
	@Test
	public void testWriteTwoObjects() throws Exception {
		Car car = new Car();
		car.setBrandName("Ford");
		car.setDate(new Date());
		car.setPrice(123456789);
		Car car2 = new Car();
		car2.setBrandName("Mazda");
		car2.setDate(new Date());
		car2.setPrice(12345678);
		List<IObject> writeList = new ArrayList<IObject>();
		writeList.add(car);
		writeList.add(car2);
		IWriter w = new BinaryWriter(System.out);
		w.write(writeList, Car.class);
	}
	
	@Test
	public void testWriteEmptyList() throws Exception {
		List<IObject> writeList = new ArrayList<IObject>();
		IWriter w = new BinaryWriter(System.out);
		w.write(writeList, Car.class);
	}
	
	@Test
	public void testWriteIncorrect() throws Exception {
		List<IObject> writeList = new ArrayList<IObject>();
		BadCar car = new BadCar();
		car.setBrandName("Lada");
		car.setDate(new Date());
		car.setPrice(1);
		writeList.add(car);
		IWriter w = new BinaryWriter(System.out);
		try {
			w.write(writeList, BadCar.class);
		} catch (Exception e){
			assertTrue(e instanceof UnsupportedEntityException);
		}
	}


}
