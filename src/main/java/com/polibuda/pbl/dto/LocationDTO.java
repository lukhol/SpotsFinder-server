package com.polibuda.pbl.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDTO implements Serializable {

	private static final long serialVersionUID = -7569061807008183056L;

	private Long id;	
	private double longitude;	
	private double latitude;
}
