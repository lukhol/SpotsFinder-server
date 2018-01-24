package com.lukhol.spotsfinder.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

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
public class WrongPlaceReportDTO implements Serializable {
	
	private static final long serialVersionUID = 6311152115423270217L;
	
	@Min(1)
	private long placeId;
	@Min(value = 1, message = "Version of reporting place cannot be empty.")
	private long placeVersion;
	private long userId;
	@NotBlank(message = "Device Id cannot be empty.")
	private String deviceId;
	private String reasonComment;
	private boolean isNotSkateboardPlace;	
}
