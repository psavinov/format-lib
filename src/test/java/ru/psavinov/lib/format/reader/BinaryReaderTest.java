package ru.psavinov.lib.format.reader;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.IReader;
import ru.psavinov.lib.format.exception.UnsupportedFormatException;
import ru.psavinov.lib.format.object.Car;
import ru.psavinov.lib.format.reader.BinaryReader;

public class BinaryReaderTest {

	private static final String BIN_VALID = "/ru/psavinov/lib/format/reader/cars_1.bin";
	private static final String BIN_EMPTY = "/ru/psavinov/lib/format/reader/cars_empty.bin";
	private static final String BIN_TWO = "/ru/psavinov/lib/format/reader/cars_2.bin";
	private static final String UNSUPPORTED = "/ru/psavinov/lib/format/reader/cars_2.xml";
	
	
	@Test
	public void testReadObjects() throws Exception {
		IReader r = new BinaryReader(
				this.getClass().getResourceAsStream(BIN_VALID));
		List<IObject> list = r.read(Car.class);
		assertEquals(1,list.size());

		IReader r2 = new BinaryReader(
				this.getClass().getResourceAsStream(BIN_TWO));
		List<IObject> list2 = r2.read(Car.class);
		assertEquals(2,list2.size());

	}
	
	@Test
	public void testEmptyFile() throws Exception {
		IReader r = new BinaryReader(
				this.getClass().getResourceAsStream(BIN_EMPTY));
		List<IObject> list = r.read(Car.class);
		assertEquals(0,list.size());
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testUnsupportedFormat() throws Exception {
		IReader r = new BinaryReader(
				this.getClass().getResourceAsStream(UNSUPPORTED));
		try {
			List<IObject> list = r.read(Car.class);
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedFormatException);
		}
	}
}
