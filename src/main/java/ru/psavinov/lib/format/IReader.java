package ru.psavinov.lib.format;

import java.util.List;

import ru.psavinov.lib.format.exception.InvalidPropertyException;
import ru.psavinov.lib.format.exception.UnsupportedEntityException;
import ru.psavinov.lib.format.exception.UnsupportedTypeException;

/**
 * Reader interface for format library
 * 
 * @author Pavel Savinov
 *
 */
public interface IReader {

	/**
	 * Retrieve list of objects of specified class
	 * 
	 * @param clazz Class of objects to retrieve
	 * 
	 * @return List of objects of specified class, or empty list if no objects available
	 * 
	 * @throws InvalidPropertyException - Source contains invalid property, which is not defined for the class
	 * @throws UnsupportedEntityException - Entity doesn't contains set/get methods for some properties
	 * @throws UnsupportedTypeException - Entity contains fields of unsupported types (@see ru.psavinov.lib.format.ETypes)
	 * @throws Exception - Parsing, SAX, DOM or other format - specific exceptions
	 */
	@SuppressWarnings("rawtypes")
	public List<IObject> read(Class clazz) throws UnsupportedTypeException, UnsupportedEntityException, InvalidPropertyException, Exception;
}
