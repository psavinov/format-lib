package ru.psavinov.lib.format;

import ru.psavinov.lib.format.exception.InvalidPropertyException;
import ru.psavinov.lib.format.exception.UnsupportedEntityException;

/**
 * Readable/writable object interface
 * 
 * @author Pavel Savinov
 *
 */
public interface IObject {
	
	/**
	 * Set object's property
	 * 
	 * @param propertyName Name of property to set
	 * @param value Property value to set
	 * 
	 * @throws InvalidPropertyException - There is no such property in the object
	 * @throws UnsupportedEntityException - Entity doesn't contains required get/set methods for all properties
	 */
	public void setProperty(String propertyName, Object value) throws InvalidPropertyException, UnsupportedEntityException;
	
	/**
	 * Get object's property value
	 * 
	 * @param propertyName Name of property to get
	 * 
	 * @return Property value, instance of class as defined in the object
	 * 
	 * @throws InvalidPropertyException - There is no such property in the object
	 * @throws UnsupportedEntityException - Entity doesn't contains required get/set methods for all properties
	 */
	public Object getProperty(String propertyName) throws InvalidPropertyException, UnsupportedEntityException;

}
