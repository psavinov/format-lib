package ru.psavinov.lib.format.object;

import java.util.Date;

/**
 * Car object for format library
 * 
 * @author Pavel Savinov
 *
 */
public class Car extends AbstractObject {

	private String brandName;
	private Date date;
	private Integer price;
		
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brand) {
		this.brandName = brand;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
				
}
