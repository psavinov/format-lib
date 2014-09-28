package ru.psavinov.lib.format.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.IReader;
import ru.psavinov.lib.format.IWriter;
import ru.psavinov.lib.format.object.Car;
import ru.psavinov.lib.format.reader.BinaryReader;
import ru.psavinov.lib.format.reader.XMLReader;
import ru.psavinov.lib.format.writer.BinaryWriter;
import ru.psavinov.lib.format.writer.XMLWriter;

/**
 * Format library usage example
 * 
 * @author Pavel Savinov
 *
 */
public class Example {
	
	public static void main(String[] args) throws Exception{
		/* list of Car objects */
		List<IObject> carList = new ArrayList<IObject>();
		Car car = new Car();
		car.setBrandName("Toyota");
		car.setDate(new Date());
		car.setPrice(40000);
		carList.add(car);
		
		/* two different IWriter implementations */
		IWriter xmlWriter = new XMLWriter(new FileOutputStream("test_car.xml"));
		IWriter binWriter = new BinaryWriter(new FileOutputStream("test_car.bin"));
		
		/* writing same list in two formats */
		xmlWriter.write(carList, Car.class);
		binWriter.write(carList, Car.class);
		
		/* two different IReader implementations */
		IReader xmlReader = new XMLReader(new FileInputStream("test_car.xml"));
		IReader binReader = new BinaryReader(new FileInputStream("test_car.bin"));
		
		/* reading card from two files */
		List<IObject> list1 = xmlReader.read(Car.class);
		List<IObject> list2 = binReader.read(Car.class);
		
		/* iterate over lists to show objects */
		for (IObject obj : list1){
			if (obj instanceof Car) {
				Car c = (Car) obj;
				System.out.println(c.getBrandName());
				System.out.println(c.getDate());
				System.out.println(c.getPrice());
			}
		}
		System.out.println();
		for (IObject obj : list2){
			if (obj instanceof Car) {
				Car c = (Car) obj;
				System.out.println(c.getBrandName());
				System.out.println(c.getDate());
				System.out.println(c.getPrice());
			}
		}		
		
		/* ExampleObject test */
		ExampleObject example = new ExampleObject();
		example.setBooleanField(true);
		example.setByteField((byte)100);
		example.setDateField(new Date());
		example.setIntegerField(999);
		example.setStringField("Новая строка, Línea nueva, New line");
		List<IObject> exampleList = new ArrayList<IObject>();
		exampleList.add(example);
		
		IWriter xmlWriter2 = new XMLWriter(new FileOutputStream("test_example.xml"));
		IWriter binWriter2 = new BinaryWriter(new FileOutputStream("test_example.bin"));
		xmlWriter2.write(exampleList, ExampleObject.class);
		binWriter2.write(exampleList, ExampleObject.class);
		
		/* Reading two ExampleObject's lists from different formats */
		IReader xmlReader2 = new XMLReader(new FileInputStream("test_example.xml"));
		IReader binReader2 = new BinaryReader(new FileInputStream("test_example.bin"));
		List<IObject> exList1 = xmlReader2.read(ExampleObject.class);
		List<IObject> exList2 = binReader2.read(ExampleObject.class);
		
		/* ExampleObject properties print */
		System.out.println();
		for (IObject obj : exList1) {
			if (obj instanceof ExampleObject) {
				ExampleObject e = (ExampleObject)obj;
				System.out.println(e.getStringField());
				System.out.println(e.getIntegerField());
				System.out.println(e.getDateField());
				System.out.println(e.getByteField());
				System.out.println(e.getBooleanField());
			}
		}
		
		System.out.println();
		for (IObject obj : exList2) {
			if (obj instanceof ExampleObject) {
				ExampleObject e = (ExampleObject)obj;
				System.out.println(e.getStringField());
				System.out.println(e.getIntegerField());
				System.out.println(e.getDateField());
				System.out.println(e.getByteField());
				System.out.println(e.getBooleanField());
			}
		}
		
	}

}
