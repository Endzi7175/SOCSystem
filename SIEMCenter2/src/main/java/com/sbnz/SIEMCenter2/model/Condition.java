package com.sbnz.SIEMCenter2.model;

import java.util.Date;

public class Condition {
	public ComapreOperator comapreOperator;
	public BooleanTrailingOperator trailingOperator;
	public String field;
	public Object value;
	
	
	public ComapreOperator getComapreOperator() {
		return comapreOperator;
	}

	public void setComapreOperator(ComapreOperator comapreOperator) {
		this.comapreOperator = comapreOperator;
	}

	public void setTrailingOperator(BooleanTrailingOperator trailingOperator) {
		this.trailingOperator = trailingOperator;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public static enum ComapreOperator{
		EQUAL_TO, NOT_EQUAL_TO, LESS_THAN_OR_EQUAL_TO,GREATER_THAN_OR_EQUAL_TO,LESS_THAN,GREATER_THAN
	}
	
	public static enum BooleanTrailingOperator{
		AND, OR
	}

	public ComapreOperator getOperator() {
		// TODO Auto-generated method stub
		return ComapreOperator.NOT_EQUAL_TO;
	}

	public String getField() {
		// TODO Auto-generated method stub
		return field;
	}

	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public BooleanTrailingOperator getTrailingOperator() {
		// TODO Auto-generated method stub
		return trailingOperator;
	}

}
