package ru.psavinov.lib.format.example;

import java.util.Date;

/**
 * Sample object with all supported types
 * 
 * @author Pavel Savinov
 *
 */
public class ExampleObject extends
		ru.psavinov.lib.format.object.AbstractObject {

	private Integer integerField;
	private Boolean booleanField;
	private String stringField;
	private Byte byteField;
	private Date dateField;
	
	public Integer getIntegerField() {
		return integerField;
	}
	
	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}
	
	public Boolean getBooleanField() {
		return booleanField;
	}
	
	public void setBooleanField(Boolean booleanField) {
		this.booleanField = booleanField;
	}
	
	public String getStringField() {
		return stringField;
	}
	
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
	
	public Byte getByteField() {
		return byteField;
	}
	
	public void setByteField(Byte byteField) {
		this.byteField = byteField;
	}
	
	public Date getDateField() {
		return dateField;
	}
	
	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}
	
	

}
