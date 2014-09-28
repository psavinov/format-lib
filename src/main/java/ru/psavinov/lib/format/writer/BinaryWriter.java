package ru.psavinov.lib.format.writer;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.IWriter;
import ru.psavinov.lib.format.util.PropertyUtil;

/**
 * Writer implementation for binary file format
 * 
 * @author Pavel Savinov
 *
 */
public class BinaryWriter implements IWriter {

	private static final byte[] header = new byte[]{(byte)(0x2526 & 0xff),(byte)((0x2526 >> 8) & 0xff)};
	
	private OutputStream stream;
	
	public BinaryWriter(OutputStream s){
		this.setStream(s);
	}
	
	/**
	 * @see ru.psavinov.lib.format.IWriter.write
	 */
	@SuppressWarnings("rawtypes")
	public void write(List<IObject> objects, Class clazz) throws Exception {
		OutputStream s = getStream();
		s.write(header);
		Integer rc = new Integer(objects.size());
		byte[] recordsCount = new byte[] {
				(byte)(rc & 0xff),
				(byte)((rc >> 8 ) & 0xff),
				(byte)((rc >> 16) & 0xff),
				(byte)((rc >> 24) & 0xff)
			};
		s.write(recordsCount);
		for (IObject obj : objects) {
			if (clazz.isInstance(obj)) {
				for (Field f : clazz.getDeclaredFields()) {
					s.write(PropertyUtil.getBytes(
							obj.getProperty(f.getName()),
							f.getType()));
				}
			}
		}
		s.close();

	}
	
	public OutputStream getStream() {
		return stream;
	}

	public void setStream(OutputStream stream) {
		this.stream = stream;
	}

}
