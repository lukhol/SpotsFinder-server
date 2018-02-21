package com.lukhol.spotsfinder.config;

import java.util.Locale;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
/*
 * @EnableWebMvc //Don’t use @EnableWebMvc in Spring Boot, just include spring-web as a maven/gradle dependency and it will be autoconfigured. 
 * source: https://dzone.com/articles/spring-boot-enablewebmvc-and-common-use-cases
 * 
 * Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath.
 * This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
 * source: https://spring.io/guides/gs/spring-boot/
 * 
 * Conclusion: In spring boot i cannot add @EnableWebMvc. I have to add class which is extending WebMvcConfigurerAdapter
 * and annotate it with @Configuration or @Component.
*/
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource  messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.forLanguageTag("en"));
	    return slr;
	}
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setForceEncoding(true);
	    characterEncodingFilter.setEncoding("UTF-8");
	    registrationBean.setFilter(characterEncodingFilter);
	    return registrationBean;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/images/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/WEB-INF/views/",
                        "classpath:/static/images/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }
}
