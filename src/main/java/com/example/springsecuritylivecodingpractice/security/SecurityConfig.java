package com.example.springsecuritylivecodingpractice.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springsecuritylivecodingpractice.security.exception.handler.CustomAuthenticationEntryPoint;
import com.example.springsecuritylivecodingpractice.security.jwt.JwtAuthenticationFilter;
import com.example.springsecuritylivecodingpractice.security.jwt.JwtAuthenticationProvider;
import com.example.springsecuritylivecodingpractice.security.login.LoginAuthenticationFilter;
import com.example.springsecuritylivecodingpractice.security.login.LoginAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST_GUEST = {"/", "/csrf", "/error", "/login", "/register", "/oauth2/**"};

	private final LoginAuthenticationProvider loginAuthenticationProvider;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public AuthenticationEntryPointFailureHandler authenticationEntryPointFailureHandler() {
		return new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
	}

	public LoginAuthenticationFilter loginFilter() throws Exception {
		LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter("/login");
		loginFilter.setAuthenticationManager(super.authenticationManager());
		loginFilter.setAuthenticationFailureHandler(this.authenticationEntryPointFailureHandler());
		return loginFilter;
	}

	public JwtAuthenticationFilter jwtFilter() throws Exception {
		FilterSkipMatcher matcher = new FilterSkipMatcher(List.of("/login", "/register", "/h2-console/**"));
		JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(matcher);
		jwtFilter.setAuthenticationManager(super.authenticationManager());
		jwtFilter.setAuthenticationFailureHandler(this.authenticationEntryPointFailureHandler());
		return jwtFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(loginAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.formLogin().disable()
			.cors().disable()
			.csrf().disable()
			.headers().frameOptions().disable()

			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.addFilterBefore(this.loginFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(this.jwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
