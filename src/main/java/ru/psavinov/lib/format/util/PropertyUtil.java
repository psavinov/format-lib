package ru.psavinov.lib.format.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.psavinov.lib.format.ETypes;
import ru.psavinov.lib.format.exception.UnsupportedTypeException;

/**
 * Properties conversion util based on 
 * Java Reflection API
 * 
 * @author Pavel Savinov
 *
 */
public class PropertyUtil {

	private static final String DATE_FORMAT = "dd.MM.yyyy";

	/**
	 * Convert specified property from String to clazz-defined format
	 * 
	 * @param nodeName Property name
	 * @param textContent String-represented property value
	 * @param clazz Target class
	 * 
	 * @return Object of class-defined type for specified property
	 * 
	 * @throws ParseException - Property parsing error for Date type
	 * @throws UnsupportedTypeException - Unsupported property type (@see ru.psavinov.lib.format.ETypes)
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertTo(String nodeName, String textContent,
			Class clazz) throws ParseException, UnsupportedTypeException {
		Object object = null;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getName().equalsIgnoreCase(nodeName)) {
				switch(ETypes.valueOf(field.getType().getSimpleName())) {
				case Integer:
					return Integer.valueOf(textContent);
				case String:
					return textContent;
				case Boolean:
					return Boolean.valueOf(textContent);
				case Byte:
					return Byte.valueOf(textContent);
				case Date:
					SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
					return df.parse(textContent);
				default:
					throw new UnsupportedTypeException(field.getType().getName());
				}
			}
		}
		return object;
	}

	/**
	 * Get field name based on Java Naming Convention
	 * 
	 * @param nodeName Name to convert
	 * 
	 * @return JNC-based name
	 */
	public static String getFieldName(String nodeName) {
		return (new String(nodeName.charAt(0)+"").toLowerCase().concat(
				nodeName.substring(1)));
	}

	/**
	 * Convert specified property to String from original class
	 * 
	 * @param property Property value to convert
	 * @param type Property type
	 * 
	 * @return String-representation of property
	 * 
	 * @throws UnsupportedTypeException - Unsupported property type (@see ru.psavinov.lib.format.ETypes)
	 */
	public static String convertFrom(Object property, Class<?> type) throws UnsupportedTypeException {
		ETypes t = ETypes.valueOf(type.getSimpleName());
		switch (t) {
		case Integer:
			return property.toString();
		case Boolean:
			return property.toString();
		case Byte:
			return property.toString();
		case String:
			return property.toString();
		case Date:
			SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
			Date d = (Date)property;
			return df.format(d);
		default:
			throw new UnsupportedTypeException(type.getName());
		}
	}

	/**
	 * Convert specified property to byte array from original class
	 * 
	 * @param property Property value to convert
	 * @param type Property type
	 * 
	 * @return Byte array - representation of property
	 * 
	 * @throws UnsupportedTypeException - Unsupported property type (@see ru.psavinov.lib.format.ETypes)
	 */
	public static byte[] getBytes(Object property, Class<?> type) throws UnsupportedTypeException {
		ETypes t = ETypes.valueOf(type.getSimpleName());
		switch (t) {
		case Integer:
			int o = ((Integer)property).intValue();
			byte[] iarr = new byte[] {
					(byte)((o >> 0)& 0xff),
					(byte)((o >> 8) & 0xff),
					(byte)((o >> 16) & 0xff),
					(byte)((o >> 24) & 0xff)
			};
			return iarr;
		case String:
			String s = (String)property;
			int len = s.length();
			byte[] sout = new byte[len*2+2];
			sout[0] = (byte)(len & 0xff);
			sout[1] = (byte)((len >> 8) & 0xff);
			int cnt = 2;
			for (int k=0;k<len;k++){
				char c = s.charAt(k);
				byte fb = (byte)((int)c & 0xff);
				byte lb = (byte)(((int)c >> 8) & 0xff);
				sout[cnt] = fb;
				sout[cnt+1] = lb;
				cnt += 2;
			}
			return sout;
		case Date:
			byte[] dout = new byte[8];
			Date d = (Date)property;
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(d);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			dout[0] = (byte)(day & 0xff);
			dout[1] = (byte)((day >> 8) & 0xff);
			dout[2] = (byte)(month & 0xff);
			dout[3] = (byte)((month >> 8) & 0xff);
			dout[4] = (byte)(year & 0xff);
			dout[5] = (byte)((year >> 8) & 0xff);
			dout[6] = (byte)((year >> 16) & 0xff);
			dout[7] = (byte)((year >> 24) & 0xff);
			return dout;
		case Byte:
			Byte b = (Byte) property;
			return new byte[]{b.byteValue()};
		case Boolean:
			Boolean bool = (Boolean) property;
			if (bool.booleanValue()) {
				return new byte[]{1};	
			} else {
				return new byte[]{0};
			}
		default:
			throw new UnsupportedTypeException(type.getName());
		}
	}

	/**
	 * Get next available object from stream and return object of required type 
	 * 
	 * @param s Stream to read
	 * @param type Object type
	 * 
	 * @return Object of specified type
	 * 
	 * @throws UnsupportedTypeException - Unsupported object type specified @see ru.psavinov.lib.format.ETypes
	 * @throws IOException - Stream reading error
	 */
	public static Object getObjectFromStream(InputStream s, Class<?> type) throws UnsupportedTypeException, IOException {
		ETypes t = ETypes.valueOf(type.getSimpleName());
		switch (t) {
		case Integer:
			byte[] ib = new byte[4];
			s.read(ib);
			String s3 = ib[3] != 0 ? Integer.toBinaryString(ib[3] > 0 ? ib[3] : ib[3]+256) : "00000000";
			String s2 = ib[2] != 0 ? Integer.toBinaryString(ib[2] > 0 ? ib[2] : ib[2]+256) : "00000000";
			String s1 = ib[1] != 0 ? Integer.toBinaryString(ib[1] > 0 ? ib[1] : ib[1]+256) : "00000000";
			String s0 = ib[0] != 0 ? Integer.toBinaryString(ib[0] > 0 ? ib[0] : ib[0]+256) : "00000000";
			s0 = addBits(s0);
			s1 = addBits(s1);
			s2 = addBits(s2);
			s3 = addBits(s3);
			String str = s3+s2+s1+s0;
			Integer integer = Integer.valueOf(str, 2);
			return integer;			
		case String:
			byte[] sl = new byte[2];
			s.read(sl);
			s1 = Integer.toBinaryString(sl[1] >= 0 ? sl[1] : sl[1] + 256);
			s0 = Integer.toBinaryString(sl[0] >= 0 ? sl[0] : sl[0] + 256);
			str = addBits(s1)+addBits(s0)					;
			Integer len = Integer.valueOf(str, 2);
			String string = "";
			for (int k=0;k<len;k++){
				byte[] ba = new byte[2];
				s.read(ba);
				s1 = ba[1]!=0 ? Integer.toBinaryString(ba[1] >0 ? ba[1] : ba[1] + 256) : "00000000";
				s0 = ba[0]!=0 ? Integer.toBinaryString(ba[0] >0 ? ba[0] : ba[0] + 256) : "00000000";
				str = addBits(s1) + addBits(s0);
				Integer code = Integer.valueOf(str, 2);
				string += (char)code.intValue();
			}
			return string;
		case Boolean:
			Boolean bool = null;
			byte[] ar = new byte[1];
			s.read(ar);
			if (ar[0] == 0) bool = false;
			else bool = true;
			return bool;
		case Date:
			Calendar cal = GregorianCalendar.getInstance();
			byte[] da = new byte[2];
			byte[] ma = new byte[2];
			byte[] ya = new byte[4];
			s.read(da);
			s.read(ma);
			s.read(ya);
			s1 = da[1]!=0 ? Integer.toBinaryString(da[1] > 0 ? da[1] : da[1]+256) : "00000000";
			s0 = da[0]!=0 ? Integer.toBinaryString(da[0] > 0 ? da[0] : da[0]+256) : "00000000";
			str = addBits(s1)+addBits(s0);
			Integer day = Integer.valueOf(str, 2);
			cal.set(Calendar.DAY_OF_MONTH, day);
			s1 = ma[1]!=0 ? Integer.toBinaryString(ma[1] > 0 ? ma[1] : ma[1]+256) : "00000000";
			s0 = ma[0]!=0 ? Integer.toBinaryString(ma[0] > 0 ? ma[0] : ma[0]+256) : "00000000";
			str = addBits(s1)+addBits(s0);
			Integer month = Integer.valueOf(str, 2);
			cal.set(Calendar.MONTH, month-1);
			s3 = ya[3]!=0 ? Integer.toBinaryString(ya[3] > 0 ? ya[3] : ya[3]+256) : "00000000";
			s2 = ya[2]!=0 ? Integer.toBinaryString(ya[2] > 0 ? ya[2] : ya[2]+256) : "00000000";
			s1 = ya[1]!=0 ? Integer.toBinaryString(ya[1] > 0 ? ya[1] : ya[1]+256) : "00000000";
			s0 = ya[0]!=0 ? Integer.toBinaryString(ya[0] > 0 ? ya[0] : ya[0]+256) : "00000000";
			str = addBits(s3)+addBits(s2)+addBits(s1)+addBits(s0);
			Integer year = Integer.valueOf(str, 2);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
		case Byte:
			byte[] barr = new byte[1];
			s.read(barr);
			return new Byte(barr[0]);
		default:
			throw new UnsupportedTypeException(type.getName());		
		}
	}

	private static String addBits(String s0) {
		if (s0.length()<8){
			String s="";
			while(s.length()+s0.length()!=8){
				s+="0";
			}
			return s+s0;
		} else {
			return s0;
		}
	}
	
	

}
