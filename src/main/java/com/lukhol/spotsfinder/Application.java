package com.lukhol.spotsfinder;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication 
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	@Bean(name="emailProperties")
	public Properties emailProperties(){
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		return properties;
	}
	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
//		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
//		emfb.setDataSource(dataSource);
//		emfb.setJpaVendorAdapter(jpaVendorAdapter);
//		return emfb;
//	}
}