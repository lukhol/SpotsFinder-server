package com.lukhol.spotsfinder.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.lukhol.spotsfinder.model.listeners.PlaceEntityListener;

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
@org.hibernate.annotations.DynamicUpdate
public class Place {

	@Id
	@Column(name="PLACE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	private Long id;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="FK_PLACE_ID", foreignKey = @ForeignKey(name = "place_image"))
	private List<Image> images;
	
	@Lob
	@Column(name="MAIN_PHOTO", columnDefinition="mediumblob", nullable = false)
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
	
	@org.hibernate.annotations.Formula("DATEDIFF(NOW(), creation_time)")
	private int daysOld;
	
	@org.hibernate.annotations.Formula("CASE WHEN DATEDIFF(NOW(), creation_time) > 60 THEN true ELSE false END")
	private boolean showInArticle;
	
	@Column(columnDefinition="DATETIME(3)")
	@org.hibernate.annotations.CreationTimestamp
	private Date creationTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "place_owner"))
	private User owner;
	
	@JsonIgnore
	@OneToMany(mappedBy="place", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<WrongPlaceReport> wrongPlaceReports;
}
