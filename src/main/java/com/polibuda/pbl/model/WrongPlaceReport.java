package com.polibuda.pbl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="wrong_place_report_id")
	private Long id;

	@OneToOne
	@JoinColumn(nullable = false)
	private Place place;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private User user;
	
	private String reasonComment;
	private long reportedPlaceVersion;
	private String deviceId;
	private boolean isNotSkateboardPlace;
}
