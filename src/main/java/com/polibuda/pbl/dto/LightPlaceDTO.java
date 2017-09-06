package com.polibuda.pbl.dto;

import java.io.Serializable;

import com.polibuda.pbl.model.Image;

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
public class LightPlaceDTO implements Serializable {
	
	private static final long serialVersionUID = 2494012469351998636L;
	
	private Long id;
	private String mainPhoto;
	private String name;
	private String description;
	@Builder.Default
	private CoordinatesDTO location = new CoordinatesDTO();
	private int type;
}
