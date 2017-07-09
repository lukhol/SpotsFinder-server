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
public class SkateSpotDto implements Serializable {

	private static final long serialVersionUID = -3209056985840394608L;
	
	private long id;
	private PlaceDto place;
}
