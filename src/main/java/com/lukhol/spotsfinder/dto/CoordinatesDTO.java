package com.lukhol.spotsfinder.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;

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

	@Range(min = -180, max = 180)
	private double longitude;
	@Range(min = -90, max = 90)
	private double latitude;
}
