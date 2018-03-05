package com.lukhol.spotsfinder.config;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lukhol.spotsfinder.repository.BootstrapperRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Bootstrapper {
	
	@Autowired
	BootstrapperRepository bootstrapperRepository;
	
	@Value("${server.baseurl}")
	String baseUrl;
	
	@PostConstruct
	public void crateOAuthTables() {
		bootstrapperRepository.createOAuthTables();
		createAvatarDirectories();
		
		log.info(baseUrl);
	}
	
	private void createAvatarDirectories() {
		File file = new File("/SpotsFinder/Avatars");
		if(!file.exists()) {
			file.mkdirs();
		}
	}
}
