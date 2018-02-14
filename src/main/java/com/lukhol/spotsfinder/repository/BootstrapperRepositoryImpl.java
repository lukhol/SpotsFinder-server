package com.lukhol.spotsfinder.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class BootstrapperRepositoryImpl implements BootstrapperRepository{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void createOAuthTables() {
		//Query queryAccessTokenTable = entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS spots_finder1.oauth_access_token ( token_id VARCHAR(255), token LONG VARBINARY, authentication_id VARCHAR(255) PRIMARY KEY, user_name VARCHAR(255), client_id VARCHAR(255), authentication LONG VARBINARY, refresh_token VARCHAR(255) );");
		//Query queryRefreshTokenTable = entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS spots_finder1.oauth_refresh_token ( token_id VARCHAR(255), token LONG VARBINARY, authentication LONG VARBINARY );");
	
		//queryAccessTokenTable.executeUpdate();
		//queryRefreshTokenTable.executeUpdate();
	}

}
