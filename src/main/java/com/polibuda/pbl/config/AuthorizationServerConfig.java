package com.polibuda.pbl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private final String clientId = "spotfinder";
	private final String clientSecret = "spotfinderSecret";
	private final String grantType = "password";
	private final String scopeRead = "read";
	private final String scopeWrite = "write";
	private final String resourceIds = "resourcesId";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
			.inMemory()
			.withClient(clientId)
			.secret(clientSecret)
			.authorizedGrantTypes(grantType, "refresh_token", "client-credential")
			.scopes(scopeRead, scopeWrite)
			.resourceIds(resourceIds);
	}
	
	 @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints
	        	.authenticationManager(authenticationManager)
	        	.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	    }
}
