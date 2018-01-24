package com.lukhol.spotsfinder.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MobileAppError {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long errorId;
	
	@Embedded
	private DeviceInfo deviceInfo;
	
	private String className;
	private String message;
	private String whereOccurred;
	
	@Column(columnDefinition="TEXT")
	private String stackTraceString;
}
