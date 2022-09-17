package com.example.springsecuritylivecodingpractice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springsecuritylivecodingpractice.security.login.LoginAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	public LoginAuthenticationFilter loginFilter(AuthenticationManager manager) {
		LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter("/login");
		loginFilter.setAuthenticationManager(manager);
		return loginFilter;
	}

	@Bean
	public SecurityFilterChain authenticationFilterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
		return http
			.httpBasic().disable()
			.formLogin().disable()
			.cors().disable()
			.csrf().disable()
			.headers().frameOptions().disable()

			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.addFilterBefore(this.loginFilter(manager), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
