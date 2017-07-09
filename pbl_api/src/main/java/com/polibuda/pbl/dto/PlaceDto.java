package com.polibuda.pbl.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Lob;

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
public class PlaceDto implements Serializable {

	private static final long serialVersionUID = 7768588138019750034L;
	
	private byte[] image;
	private String coordinates;
}
