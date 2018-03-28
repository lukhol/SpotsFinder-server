package com.lukhol.spotsfinder.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

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
	@NotEmpty
	@Length(min=5, max=80)
	private String name;
	@NotEmpty
	@Length(min=5, max=255)
	private String description;
	@Builder.Default @Valid
	private CoordinatesDTO location = new CoordinatesDTO();
	@Range(min=0, max = 1)
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
	
	@Range(min=1)
	private long userId;
}
