package com.polibuda.pbl.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/welcome")
public class NonSecureRestEndpoint {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping
	public String welcome(@RequestParam String code){
		log.info("Welcome executed, {}", code);
		return "welcome";
	}
	
	@GetMapping("/{password}")
	public String getPasswordEncoded(@PathVariable String password){
		return passwordEncoder.encode(password);
	}
}
