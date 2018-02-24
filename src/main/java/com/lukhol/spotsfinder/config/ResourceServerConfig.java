package com.lukhol.spotsfinder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    private final String resourceIds = "resourcesId";

	@Autowired
	MyAuthenticationProvider myAuthenticationProvider;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
        	.resourceId(resourceIds);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http
    		.authenticationProvider(myAuthenticationProvider)
        	.authorizeRequests()
	        	.antMatchers(HttpMethod.POST, "/places").authenticated()//.access("hasRole('USER') or hasRole('ADMIN')")
	        	.antMatchers(HttpMethod.PUT, "/places/**").authenticated()//.access("hasRole('USER') or hasRole('ADMIN')")
	        	.antMatchers(HttpMethod.DELETE, "/places/**").authenticated();
    }
    
}