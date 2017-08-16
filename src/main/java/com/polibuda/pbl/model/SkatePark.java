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
@Table(name="SKATE_PARKS")
public class SkatePark {

	@Id
	@Column(name="SKATE_PARK_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private List<Image> images;
	private Location location;
}
