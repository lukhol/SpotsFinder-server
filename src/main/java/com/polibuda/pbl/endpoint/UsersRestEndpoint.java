package com.polibuda.pbl.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersRestEndpoint {

	
	@GetMapping
	public String test(){
		return "test";
	}
}
