package com.lukhol.spotsfinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class WrongPlaceReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	@Column(name="wrong_place_report_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "reported_place")) //Name for db constraint
	private Place place;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, foreignKey = @ForeignKey(name = "reporting_user"))
	private User user;
	
	private String reasonComment;
	private long reportedPlaceVersion;
	private String deviceId;
	private boolean isNotSkateboardPlace;
}
