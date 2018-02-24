package com.lukhol.spotsfinder.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final String clientId = "spotfinder";
	private final String clientSecret = "spotfinderSecret";
	private final String scopeRead = "read";
	private final String scopeWrite = "write";
	private final String resourceIds = "resourcesId";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
			.inMemory()
			.withClient(clientId).secret(clientSecret)
			.authorizedGrantTypes("password", "refresh_token", "client_credential")
			.authorities("ROLE_CLIENT")
			.scopes(scopeRead, scopeWrite)
			.resourceIds(resourceIds)
			.accessTokenValiditySeconds(60*60*24*30)
			.refreshTokenValiditySeconds(60*60*24*365);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.userDetailsService(userDetailsService)
			.authenticationManager(authenticationManager)
			.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
			//.pathMapping(defaultPath, customPath) // There I can set custom path mapping 
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.checkTokenAccess("permitAll()"); //Pozwala na konfiguracjê endpointu, oauth/token, oauth/check_token
	}
	
	@Bean
	public TokenStore tokenStore(){
		return new JdbcTokenStore(dataSource);
	}
}
