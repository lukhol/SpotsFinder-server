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
public class PlaceSearchDTO implements Serializable {

	private static final long serialVersionUID = 1109896777676432420L;

	private AddressDTO location;
	private int distance;
	private int[] type;
	private boolean gap;
	private boolean stairs;
	private boolean rail;
	private boolean ledge;
	private boolean handrail;
	private boolean corners;
	private boolean manualpad;
	private boolean wallride;
	private boolean downhill;
	private boolean openYourMind;
	private boolean pyramid;
	private boolean curb;
	private boolean bank;
	private boolean bowl;
}
