package com.gabil.kargo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gabil.kargo.security.filter.FirebaseTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final FirebaseTokenFilter firebaseTokenFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/signup", "/api/auth/login").permitAll()
						.requestMatchers("/api/user/**").hasAnyRole("DRIVER","ADMIN")
						.requestMatchers("/admin/**","/api/admin/**").hasRole("ADMIN")
						.requestMatchers("/driver/**", "/api/driver/**").hasRole("DRIVER")
						.anyRequest().authenticated()
				)
				.addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
