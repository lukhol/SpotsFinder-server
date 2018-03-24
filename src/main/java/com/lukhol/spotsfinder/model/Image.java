package com.lukhol.spotsfinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="IMAGES")
public class Image{
	
	@Id
	@Column(name="IMAGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	private Long id;
	
	@Lob
	@Column(name="IMAGE", columnDefinition="mediumblob")
	private String image;
	
	@JsonIgnore
	@JoinColumn(name="FK_PLACE_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Place place;
}
