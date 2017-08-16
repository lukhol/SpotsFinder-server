package com.polibuda.pbl.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name="SKATE_SPOTS")
public class SkateSpot {

	@Id
	@Column(name="SKATE_SPOT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private List<Image> images;
	
	private Location location;
	
	@Column(name="NAME", length=30)
	private String name;
	
	@Column(name="DESCRIPTION", length=255)
	private String description;
	
	@Column(name="TYPE", precision=2, scale=0)
	private int type;
	
	@Column(name="GAP")
	private boolean gap;
	
	@Column(name="STAIRS")
	private boolean stairs;
	
	@Column(name="RAIL")
	private boolean rail;
	
	@Column(name="LEDGE")
	private boolean ledge;
	
	@Column(name="HANDRAIL")
	private boolean handrail;
	
	@Column(name="CORNERS")
	private boolean corners;
	
	@Column(name="MANUALPAD")
	private boolean manualpad;
	
	@Column(name="WALLRIDE")
	private boolean wallride;
	
	@Column(name="DOWNHILL")
	private boolean downhill;
	
	@Column(name="OPEN_YOUR_MIND")
	private boolean openYourMind;
	
	@Column(name="PYRAMID")
	private boolean pyramid;
	
	@Column(name="CURB")
	private boolean curb;
	
	@Column(name="BANK")
	private boolean bank;
	
	@Column(name="BOWL")
	private boolean bowl;
}
