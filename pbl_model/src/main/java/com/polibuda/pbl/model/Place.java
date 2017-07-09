package com.polibuda.pbl.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

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
@Embeddable
public class Place {

	@Lob
    @Column(name="PLACE_IMAGE", nullable=false, columnDefinition="mediumblob")
    private byte[] image;
	
	@Column(name="COORDINATES")
	private String coordinates;
}
