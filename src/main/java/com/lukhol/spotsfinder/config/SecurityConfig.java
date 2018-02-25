package com.lukhol.spotsfinder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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
@Order(2) 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private String realmName = "SpotsFinder API";
	
	@Value("${user.home}")
	String userHome;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	MyAuthenticationProvider myAuthenticationProvider;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		System.out.println(userHome);
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(myAuthenticationProvider) //Je¿eli wy³¹cze wszystkie authProvidery to u¿ywany bêdzie userDetailsService do logowania.
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}

	/**
	 * Note: if your Authorization Server is also a Resource Server then there is another security filter chain with lower priority controlling the API resources.
	 * For those requests to be protected by access tokens you need their paths not to be matched by the ones in the main user-facing filter chain, so be sure to 
	 * include a request matcher that picks out only non-API resources in the WebSecurityConfigurer above.
	 * 
	 * But why does not work?!
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.csrf().disable()
				.httpBasic().realmName(realmName)
			.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/places/**", "/welcome").authenticated();
	}
	
	//@Order(1)
	//@Configuration
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
	
	//@Order(2)
	//@Configuration
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
	
	//@Order(4)
	//@Configuration
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