package com.lukhol.spotsfinder.exception;

import com.lukhol.spotsfinder.model.User;

public class NotFoundUserException extends Exception {
	
	private static final long serialVersionUID = 328082720816826736L;
	
	public NotFoundUserException() {
		super();
	}
	
	public NotFoundUserException(String message){
		super(message);
	}

	/**
	 * Generate exception with message "Could not find external user. FacebookId: {facebookId}, GoogleId: {googleId}".
	 * Use when cannot find external user by facebook or google id. 
	 * 
	 * @param user
	 */
	public NotFoundUserException(User user) {
		super(
				new StringBuilder(5)
					.append("Could not find external user. ")
					.append("FacebookId: ")
					.append(user.getFacebookId())
					.append(", GoogleId: ")
					.append(user.getGoogleId())
					.toString()
		);
	}
	
	/**
	 * Generate exception with message "Not found user with provided identifier (id)".
	 * Use when cannot find user with database identifier.
	 * 
	 * @param id - database identifier of user
	 */
	public NotFoundUserException(long id) {
		super(
				new StringBuilder(3)
					.append("Not found user with provided identifier (")
					.append(id)
					.append(").")
					.toString()
		);
	}
}
