package com.lukhol.spotsfinder.service.user;

import java.util.Optional;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.ResetPasswordException;
import com.lukhol.spotsfinder.model.AccountRecover;

public interface UserPasswordService {
	Optional<AccountRecover> findOneByGuid(String guid);
	void recoverAccount(String email) throws NotFoundUserException;
	void resetPassword(String code, String email, String newPassword) throws ResetPasswordException, NotFoundUserException;
}
