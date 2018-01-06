package com.polibuda.pbl.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {
	
	private String idiom;
	private String model;
	private String version;
	private String versionNumber;
	private String platform;
}
