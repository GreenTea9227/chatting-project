package com.kor.syh.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kor.syh.common.jwt.JwtUtils;
import com.kor.syh.member.security.CustomAccessDeniedHandler;
import com.kor.syh.member.security.CustomAuthenticationEntryPoint;
import com.kor.syh.member.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ObjectMapper objectMapper;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable())
			.cors(c -> c.disable())
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(f -> f.disable())
			.httpBasic(h -> h.disable())
			.authorizeHttpRequests(r -> r.requestMatchers("/register", "/login").permitAll()
										 .anyRequest().authenticated())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(e -> e.accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
									 .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)));

		return http.build();
	}
}
