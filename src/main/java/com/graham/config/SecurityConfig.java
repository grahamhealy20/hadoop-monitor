package com.graham.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void cofigureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication().withUser("user@ittd.ie").password("password").roles("USER");
	}
	
	// Web Application Security Configuration
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable();
		
			http.authorizeRequests()
						.antMatchers("/resources/**").permitAll()
						.anyRequest().authenticated()
						.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
						.and()
				.httpBasic();
		
		
//		http.authorizeRequests()
//		.antMatchers("/resources/**").permitAll()
//		.anyRequest().authenticated()
//		.and()
//.formLogin()
//	.loginPage("/login")
//	.permitAll()
//		.and()
//.httpBasic();
		
	}
}
