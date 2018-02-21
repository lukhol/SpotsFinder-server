package com.lukhol.spotsfinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name="IMAGES")
public class Image {
	
	@Id
	@Column(name="IMAGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	private Long id;
	
	@Lob
	@Column(name="IMAGE", columnDefinition="mediumblob")
	private String image;
}