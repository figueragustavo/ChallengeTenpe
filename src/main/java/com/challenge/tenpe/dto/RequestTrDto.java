package com.challenge.tenpe.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestTrDto {
	
	private int page;
	
	private int size;

}
