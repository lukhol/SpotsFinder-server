package com.lukhol.spotsfinder.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
public class PlaceRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	@Getter @Setter
	private Long id;
	
	@JoinColumn(name = "place_id", foreignKey = @ForeignKey(name = "rating_place"))
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@Getter @Setter
	private Place place;

	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "rating_user"))
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@Getter @Setter
	private User user;

	@Getter @Setter
	int rate;
}