package com.ticketbooking.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ticketbooking.model.enums.TripStatus;

import lombok.Builder;
import lombok.Data;

@JsonInclude(content = Include.NON_NULL)
@Data
@Builder
public class UpdateTripRequest {
	
	private Integer id;
	private String from;
	private String to;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime departureDate;
	private Integer price;
	private TripStatus tripStatus;
	
}
