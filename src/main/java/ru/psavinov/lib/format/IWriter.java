package ru.psavinov.lib.format;

import java.util.List;

/**
 * Writer interface for format library
 * 
 * @author Pavel Savinov
 *
 */
public interface IWriter {
	
	/**
	 * Write specified object's list to specified destionation
	 * 
	 * @param objects List of objects to write
	 * 
	 * @param clazz
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void write(List<IObject> objects, Class clazz) throws Exception;
}
