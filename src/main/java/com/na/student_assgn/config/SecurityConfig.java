package com.na.student_assgn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.na.student_assgn.filter.StudentJWTFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private StudentJWTFilter jwtFilter;
	
//	@Autowired
//    @Qualifier("delegatedAuthenticationEntryPoint")
//    AuthenticationEntryPoint authEntryPoint;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						requests -> requests
								.requestMatchers("/authenticate", "/register", "/logout", "/refreshToken",
										"/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
										"/swagger-resources/**", "/configuration/ui", "/configuration/security",
										"/swagger-ui", "/webjars/**", "/swagger-ui/**", "/bearerAuth-openapi/**")
								.permitAll().anyRequest().authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(
//						requests -> requests
//								.requestMatchers("/authenticate", "/register", "/logout", "/refreshToken",
//										"/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
//										"/swagger-resources/**", "/configuration/ui", "/configuration/security",
//										"/swagger-ui", "/webjars/**", "/swagger-ui/**", "/bearerAuth-openapi/**")
//								.permitAll().anyRequest().authenticated())
//				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint))
//	            .exceptionHandling(Customizer.withDefaults());;
//		return httpSecurity.build();
//	}

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(requests -> requests.requestMatchers("/register").permitAll()
//			    .requestMatchers("/authenticate").permitAll()
//			    .requestMatchers("/findStudent").hasRole("STUDENT")
//			    .anyRequest()
//			    .authenticated())
//				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//		return httpSecurity.build();
//	}
}
