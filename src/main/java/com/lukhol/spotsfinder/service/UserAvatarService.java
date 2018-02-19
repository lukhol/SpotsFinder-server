package com.lukhol.spotsfinder.service;

import java.io.IOException;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.model.User;

public interface UserAvatarService {
	void saveAvatar(byte[] avatarBytes, long userId) throws IOException;
	String setInternalAvatarUrl(User user);
	byte[] getUserAvatar(long userId) throws NotFoundUserException, IOException;
}
