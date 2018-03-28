package com.lukhol.spotsfinder.endpoint;

import java.util.Map;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRestResponse {
	private Map<String, String> errors = new HashMap<>();
}