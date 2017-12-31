package com.polibuda.pbl.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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

	public Long getErrorId() {
		return errorId;
	}

	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}

	public String getWhereOccurred() {
		return whereOccurred;
	}

	public void setWhereOccurred(String whereOccurred) {
		this.whereOccurred = whereOccurred;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTraceString() {
		return stackTraceString;
	}

	public void setStackTraceString(String stackTraceString) {
		this.stackTraceString = stackTraceString;
	}
}
