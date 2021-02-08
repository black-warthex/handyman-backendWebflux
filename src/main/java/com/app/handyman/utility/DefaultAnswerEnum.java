package com.app.handyman.utility;


public enum DefaultAnswerEnum {

	MESSAGE_INSERT("successful insert"),MESSAGE_ERROR("error process failed"),
	MESSAGE_DUPLICATE("you can't add this service report, it is already registered"),
	MESSAGE_DATE_ERROR("the end date can't be less than the initial or equal");
	
	private String value;
	
	private DefaultAnswerEnum(String value) {		
		this.value=value;
	}
	public String getValue() {
		return this.value;
	}

}
