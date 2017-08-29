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
public class PlaceSearchDTO implements Serializable {

	private static final long serialVersionUID = 1109896777676432420L;

	private AddressDTO location;
	private int distance;
	private int[] type;
	private Boolean gap;
	private Boolean stairs;
	private Boolean rail;
	private Boolean ledge;
	private Boolean handrail;
	private Boolean corners;
	private Boolean manualpad;
	private Boolean wallride;
	private Boolean downhill;
	private Boolean openYourMind;
	private Boolean pyramid;
	private Boolean curb;
	private Boolean bank;
	private Boolean bowl;
}
