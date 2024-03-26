package com.na.student_assgn.dto;

public class OrderDto {
	
private String headerOrder = "AESC";
	
	private String valueOrder = "DESC";

	public OrderDto() {
		super();
	}

	public OrderDto(String headerOrder, String valueOrder) {
		super();
		this.headerOrder = headerOrder;
		this.valueOrder = valueOrder;
	}

	public String getHeaderOrder() {
		return headerOrder;
	}

	public void setHeaderOrder(String headerOrder) {
		this.headerOrder = headerOrder;
	}

	public String getValueOrder() {
		return valueOrder;
	}

	public void setValueOrder(String valueOrder) {
		this.valueOrder = valueOrder;
	}
	

}
