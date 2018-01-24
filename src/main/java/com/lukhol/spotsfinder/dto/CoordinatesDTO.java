package com.lukhol.spotsfinder.dto;

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
public class CoordinatesDTO implements Serializable {
	
	private static final long serialVersionUID = -5953551906363528087L;

	private double longitude;	
	private double latitude;
}
