package com.reflex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableConfigurationProperties
@ComponentScan("com.reflex")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			
				.antMatchers("/*").permitAll()
				//.antMatchers("/","/book/**","/auth/*").permitAll()
				//.antMatchers("/candidates/**").hasRole("superuser")
				//.antMatchers("/customer/***" ).hasRole("user")
				//.anyRequest().authenticated()
				.and()
			.httpBasic()
				.authenticationEntryPoint(authEntryPoint);
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	public void configure (AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService);
	} 
	
}
