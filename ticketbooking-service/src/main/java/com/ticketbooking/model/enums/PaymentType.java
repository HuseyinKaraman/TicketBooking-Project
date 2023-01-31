package com.ticketbooking.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentType {
	CREDITCARD,
	BANKTRANSFER;
	
	
	@JsonCreator
    public static PaymentType fromString(String key) {
        for(PaymentType paymentType : PaymentType.values()) {
            if(paymentType.name().equalsIgnoreCase(key)) {
                return paymentType;
            }
        }
        return null;
    }
	
	
	
}
