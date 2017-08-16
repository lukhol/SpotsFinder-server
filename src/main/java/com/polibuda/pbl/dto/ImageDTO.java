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
public class ImageDTO implements Serializable {

	private static final long serialVersionUID = 9144507229117834236L;

	private Long id;
	private byte[] image;
}
