package com.polibuda.pbl.dto;

import java.io.Serializable;
import java.util.List;

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
public class SkateParkDTO implements Serializable {

	private static final long serialVersionUID = -636635438124000496L;

	private Long id;
	private List<ImageDTO> images;
	private LocationDTO location;
}
