package ru.psavinov.lib.format.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.exception.InvalidPropertyException;
import ru.psavinov.lib.format.exception.UnsupportedEntityException;

/**
 * Abstarct readable/writable object implementation based
 * on Java Reflection API
 * 
 * @author Pavel Savinov
 *
 */
public abstract class AbstractObject implements IObject {

	/**
	 * @see ru.psavinov.lib.format.IObject.getProperty
	 */
	public Object getProperty (String propertyName) throws InvalidPropertyException, UnsupportedEntityException {
		Field field;
		try {
			field = this.getClass().getDeclaredField(propertyName);
			if (field != null) {
				String getterName = "get".concat((field.getName().charAt(0)+"").toUpperCase())
						.concat(field.getName().substring(1));
				Method getter = null;
				try {
					getter =  this.getClass().getDeclaredMethod(getterName);			
				} catch (NoSuchMethodException e) { /* Boolean fields can have is-getter */
					getterName = "is".concat((field.getName().charAt(0)+"").toUpperCase())
							.concat(field.getName().substring(1));
					getter =  this.getClass().getDeclaredMethod(getterName);
				}
				return getter.invoke(this);
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			throw new InvalidPropertyException("Invalid property", propertyName);
		} catch (NoSuchMethodException e) {
			throw new UnsupportedEntityException();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
				
		return propertyName;
	}
	
	/**
	 * @see ru.psavinov.lib.format.IObject.setProperty
	 */
	public void setProperty (String propertyName, Object value) throws InvalidPropertyException, UnsupportedEntityException {
		Field field;
		try {
			field = this.getClass().getDeclaredField(propertyName);
			if (field != null) {
				String setterName = "set".concat((field.getName().charAt(0)+"").toUpperCase())
						.concat(field.getName().substring(1));
				Method setter =  this.getClass().getDeclaredMethod(setterName, field.getType());			
				setter.invoke(this, value);
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			throw new InvalidPropertyException("Invalid property",propertyName);
		} catch (NoSuchMethodException e) {
			throw new UnsupportedEntityException();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
				
	}



}
