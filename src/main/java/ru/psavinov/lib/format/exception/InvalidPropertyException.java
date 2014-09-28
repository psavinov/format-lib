package ru.psavinov.lib.format.exception;

public class InvalidPropertyException extends Exception {

	private String propertyName;

	public InvalidPropertyException(String string, String p) {
		super(string);
		this.setPropertyName(p);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	private static final long serialVersionUID = -8979545311003516497L;

}
