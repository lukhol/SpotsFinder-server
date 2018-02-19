package com.lukhol.spotsfinder.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.UserRepository;

import lombok.NonNull;

@Service
public class UserAvatarServiceImpl implements UserAvatarService {
	
	@Value("${user.avatar.path}")
	private String AVATARS_PATH;
	
	private final UserRepository userRepository;
	
	public UserAvatarServiceImpl(@NonNull UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public void saveAvatar(byte[] avatarBytes, long userId) throws IOException {
		FileOutputStream fos = new FileOutputStream(String.format("%s/%d.jpg", AVATARS_PATH , userId)); 
		
		try {
		    fos.write(avatarBytes);
		}
		finally {
		    fos.close();
		}
	}

	@Override
	@Transactional
	public String setInternalAvatarUrl(User user) {
		user.setAvatarUrl(String.format(String.format("%s%d.jpg", "/user/avatar/", user.getId())));
		userRepository.save(user);
		return user.getAvatarUrl();
	}

	@Override
	@Transactional
	public byte[] getUserAvatar(long userId) throws NotFoundUserException, IOException {
		
		if(!userRepository.exists(userId))
			throw new NotFoundUserException("Not found user: " + userId);
		
		File fileImage = new File(String.format("%s/%d.jpg", AVATARS_PATH , userId));
		byte[] imageBytes;
		
		if(fileImage.exists()){
			imageBytes = Files.readAllBytes(fileImage.toPath());
		}
		else{
			File anonymousUserFile = new ClassPathResource("/static/images/anonymous.jpg").getFile();
			imageBytes = Files.readAllBytes(anonymousUserFile.toPath());
		}
		
		return imageBytes;
	}
}
