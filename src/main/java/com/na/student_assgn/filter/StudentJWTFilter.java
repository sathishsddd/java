package com.na.student_assgn.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.na.student_assgn.jwtutil.StudentJWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class StudentJWTFilter extends OncePerRequestFilter {

	@Autowired
	private StudentJWTUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

//		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = request.getRequestURI();
//		if (requestURI.endsWith("/authenticate") || requestURI.endsWith("/register") || requestURI.startsWith("/swagger-ui/**")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
		
		if (requestURI.endsWith("/authenticate") || 
			    requestURI.endsWith("/register") || 
			    requestURI.startsWith("/swagger-ui/") || 
			    requestURI.equals("/swagger-ui") ||
			    requestURI.equals("/v2/api-docs") ||
			    requestURI.equals("/v3/api-docs") ||
			    requestURI.startsWith("/v3/api-docs/") ||
			    requestURI.equals("/swagger-resources") ||
			    requestURI.startsWith("/swagger-resources/") ||
			    requestURI.equals("/configuration/ui") ||
			    requestURI.equals("/configuration/security") ||
			    requestURI.equals("/webjars") ||
			    requestURI.startsWith("/webjars/") ||
			    requestURI.equals("/bearerAuth-openapi") ||
			    requestURI.startsWith("/bearerAuth-openapi/")) {
			    filterChain.doFilter(request, response);
//			    return;
			}

//		HttpSession session = request.getSession(false);
		
//		if (session != null && session.getAttribute("loginUser") != null) {
//	         User is logged in, continue the filter chain
		try {
			String jwt = null;
			String extractUserName = null;
			String header = request.getHeader("Authorization");
			if (header != null && header.startsWith("Bearer ")) {
				jwt = header.substring(7);
				extractUserName = jwtUtil.extractUsername(jwt);
			}
			if (extractUserName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(extractUserName);
				if (jwtUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null,
							userDetails, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		} catch (ExpiredJwtException | SignatureException e) {
			resolver.resolveException(request, response, null, e);
		}
		
			filterChain.doFilter(request, response);
	   
//	    } else {
//	        // User is not logged in, return 401 Unauthorized
//	      
//	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	        response.setContentType("application/json");
////       response.getWriter().write("{\"message\": \"Session Expired\"}");
//	    }
		
	}

}
