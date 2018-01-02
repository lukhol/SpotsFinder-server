package com.polibuda.pbl.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.polibuda.pbl.model.listeners.PlaceEntityListener;

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
@EntityListeners(value = PlaceEntityListener.class)
@Table(name="PLACES")
public class Place {

	@Id
	@Column(name="PLACE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="FK_PLACE_ID")
	private List<Image> images;
	
	@Lob
	@Column(name="MAIN_PHOTO", columnDefinition="mediumblob")
	private String mainPhoto;
	
	@Column(name="NAME", length=30)
	private String name;
	
	@Column(name="DESCRIPTION", length=255)
	private String description;
	
	@Column(name="LONGITUDE")
	private double longitude;
	
	@Column(name="LATITUDE")
	private double latitude;
	
	@Column(name="TYPE")
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
	
	@Column(name="VERSION")
	private long version;
	
//	@PreUpdate
//	@PrePersist
//	public void updateVersion(){
//		Calendar calendar = Calendar.getInstance();
//		setVersion(calendar.getTimeInMillis());
//	}
}
