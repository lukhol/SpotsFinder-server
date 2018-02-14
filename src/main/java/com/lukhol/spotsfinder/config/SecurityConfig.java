package com.lukhol.spotsfinder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private String realmName = "SpotFinderRealm";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	MyAuthenticationProvider myAuthenticationProvider;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.httpBasic().realmName(realmName)
			.and()
			.csrf().disable();
	}
	
	@Order(1)
	@Configuration
	public static class SecondConfig extends WebSecurityConfigurerAdapter {
		
		 @Override
	        protected void configure(HttpSecurity http) throws Exception {
	            http
	              	.antMatcher("/user/**")
	              		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	              		.and()
		              	.httpBasic()
		              	.and()
		              	.authorizeRequests()
		              		.antMatchers("/user").authenticated()
		              		.antMatchers("/user/login").authenticated()
		              		.antMatchers("/user/login/external").authenticated()
		              	.and()
		              	.csrf().disable();
	        }
	}
	
	@Order(2)
	@Configuration
	public static class FourthConfig extends WebSecurityConfigurerAdapter {
		
		 @Override
	        protected void configure(HttpSecurity http) throws Exception {
	            http
	              	.antMatcher("/places/**")
		              	.httpBasic()
		              	.and()
		              	.authorizeRequests()
		              		.antMatchers("/places/report").authenticated()
		              		.antMatchers("/places/searches").authenticated()
		              	.and()
		              	.csrf().disable();
	        }
	}
	
	@Order(4)
	@Configuration
	public static class ThirdConfig extends WebSecurityConfigurerAdapter {
		
		 @Override
	        protected void configure(HttpSecurity http) throws Exception {
	            http
	              	.antMatcher("/errors")
	              		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	              		.and()
		              	.httpBasic()
		              	.and()
		              	.authorizeRequests().anyRequest().authenticated()
		              	.and()
		              	.csrf().disable();
	        }
	}
}
