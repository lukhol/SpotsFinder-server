package com.polibuda.pbl.dto;

import java.io.Serializable;

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
	
	private long placeId;
	private long placeVersion;
	private long userId;
	private String deviceId;
	private String reasonComment;
	private boolean isNotSkateboardPlace;	
}
