package com.polibuda.pbl.model;

import javax.persistence.Embeddable;

@Embeddable
public class DeviceInfo {
	
	private String idiom;
	private String model;
	private String version;
	private String versionNumber;
	private String platform;
	
	public String getIdiom() {
		return idiom;
	}
	
	public void setIdiom(String idiom) {
		this.idiom = idiom;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getVersionNumber() {
		return versionNumber;
	}
	
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	public String getPlatform() {
		return platform;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
