package com.ticketbooking.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
	
	MALE,
	FEMALE;
	
	@JsonCreator
    public static Gender fromString(String key) {
        for(Gender gender : Gender.values()) {
            if(gender.name().equalsIgnoreCase(key)) {
                return gender;
            }
        }
        return null;
    }
}
