package com.lukhol.spotsfinder.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private String realmName = "SpotsFinder API";
	
	@Value("${user.home}")
	String userHome;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;

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
			.csrf()
			.disable()
			.httpBasic()
		.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/places").authenticated()
				.antMatchers("/places/searches").authenticated()
		.and()
			.authorizeRequests()
				.anyRequest()
				.permitAll();
	}
	
	private static class BasicRequestMather implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader("Authorization");
			return (auth != null && auth.startsWith("Basic") || auth == null); // jezeli nie ma autha lub basic to tutaj
		}
	}
	
    @Bean()
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}