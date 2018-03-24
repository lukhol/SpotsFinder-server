package com.lukhol.spotsfinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class PlaceJson {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	private Long id;
	
	@Lob
	@Column(columnDefinition="mediumblob", nullable = false)
	private String placeJson;
}