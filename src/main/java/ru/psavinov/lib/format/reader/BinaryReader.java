package ru.psavinov.lib.format.reader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.IReader;
import ru.psavinov.lib.format.exception.InvalidPropertyException;
import ru.psavinov.lib.format.exception.UnsupportedEntityException;
import ru.psavinov.lib.format.exception.UnsupportedFormatException;
import ru.psavinov.lib.format.exception.UnsupportedTypeException;
import ru.psavinov.lib.format.util.PropertyUtil;

/**
 * Reader implementation for binary file format
 * 
 * @author Pavel Savinov
 *
 */
public class BinaryReader implements IReader {
	
	private static final byte[] HEADER = new byte[]{(byte)(0x2526 & 0xff),(byte)((0x2526 >> 8) & 0xff)};
	
	private InputStream stream;
	
	public BinaryReader(InputStream s){
		this.stream = s;
	}

	/**
	 * @see ru.psavinov.lib.format.IReader.read
	 */
	@SuppressWarnings("rawtypes")
	public List<IObject> read(Class clazz) throws UnsupportedTypeException,
			UnsupportedEntityException, InvalidPropertyException,UnsupportedFormatException, Exception {
		List<IObject> list = new ArrayList<IObject>();
		byte[] h = new byte[2];
		InputStream s = getStream();
		s.read(h);
		if (h[0] != HEADER[0] ||
				h[1] != HEADER[1]){
			throw new UnsupportedFormatException();
		}
		byte[] rc = new byte[4];
		s.read(rc);
		String str = Integer.toBinaryString(rc[3])+
				Integer.toBinaryString(rc[2])+
				Integer.toBinaryString(rc[1])+
				Integer.toBinaryString(rc[0]);
		Integer recordsCount = Integer.valueOf(str, 2);
		for (int k=0;k<recordsCount;k++){
			IObject object = (IObject) clazz.newInstance();
			for (Field f : clazz.getDeclaredFields()) {
				object.setProperty(f.getName(),
					PropertyUtil.getObjectFromStream(s,
							f.getType()));
			}
			list.add(object);
		}
		
		return list;
	}
	

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

}
