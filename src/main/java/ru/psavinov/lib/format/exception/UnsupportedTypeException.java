package ru.psavinov.lib.format.exception;

public class UnsupportedTypeException extends Exception {
	
	private static final long serialVersionUID = 738263100731923470L;

	private String typeName;
	
	public UnsupportedTypeException(String type){
		super();
		this.typeName = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
