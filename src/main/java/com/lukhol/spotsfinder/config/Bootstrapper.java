package com.lukhol.spotsfinder.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lukhol.spotsfinder.repository.BootstrapperRepository;

@Component
public class Bootstrapper {
	
	@Autowired
	BootstrapperRepository bootstrapperRepository;
	
	@PostConstruct
	public void crateOAuthTables() {
		bootstrapperRepository.createOAuthTables();
	}
	
	private void createAvatarDirectories() {
		
	}
}
