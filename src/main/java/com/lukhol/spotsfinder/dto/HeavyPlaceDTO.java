package com.lukhol.spotsfinder.dto;

import java.io.Serializable;
import java.util.List;

import com.lukhol.spotsfinder.model.Image;

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
public class HeavyPlaceDTO implements Serializable {

	private static final long serialVersionUID = -3209056985840394608L;
	
	private Long id;
	private long version;
	private List<Image> images;
	private String name;
	private String description;
	@Builder.Default
	private CoordinatesDTO location = new CoordinatesDTO();
	private int type;
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
	
	private long userId;
}
