package ru.psavinov.lib.format.reader;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.exception.InvalidPropertyException;
import ru.psavinov.lib.format.object.Car;
import ru.psavinov.lib.format.reader.XMLReader;

public class XMLReaderTest {
	
	private static final String CARS_2_FILE = "/ru/psavinov/lib/format/reader/cars_2.xml";
	private static final String CARS_EMPTY = "/ru/psavinov/lib/format/reader/cars_empty.xml";
	private static final String CARS_INVALID = "/ru/psavinov/lib/format/reader/cars_invalid.xml";

	@Test
	public void testReadCars() throws Exception {
		XMLReader r = new XMLReader(this.getClass()
				.getResourceAsStream(CARS_2_FILE));
		List<IObject> list = r.read(Car.class);
		assertEquals(2, list.size());
	}

	@Test
	public void testReadCarsClass() throws Exception {
		XMLReader r = new XMLReader(this.getClass()
				.getResourceAsStream(CARS_2_FILE));
		List<IObject> list = r.read(Car.class);
		assertTrue(list.get(0) instanceof Car);
	}
	
	@Test
	public void testReadCarProperty() throws Exception {
		XMLReader r = new XMLReader(this.getClass()
				.getResourceAsStream(CARS_2_FILE));
		List<IObject> list = r.read(Car.class);
		for (Field f : Car.class.getDeclaredFields()){
			IObject car = list.get(0);
			assertTrue(car.getProperty(f.getName()).getClass() == 
					f.getType());
		}
	}
	
	@Test
	public void testReadEmpty() throws Exception {
		XMLReader r = new XMLReader(this.getClass()
				.getResourceAsStream(CARS_EMPTY));
		List<IObject> list = r.read(Car.class);		
		assertEquals(0,list.size());
	}
	
	@Test
	public void testInvalidProperty() throws Exception {
		XMLReader r = new XMLReader(this.getClass()
				.getResourceAsStream(CARS_INVALID));
		try {
			@SuppressWarnings("unused")
			List<IObject> list = r.read(Car.class);		
		} catch (Exception e) {
			assertTrue(e instanceof InvalidPropertyException);
		}
	}


}
