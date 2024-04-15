package com.example.resource;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;



import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
@ControllerAdvice
public class PermissionAutorizationInterceptor implements HandlerInterceptor {
	@Autowired
	 GetPermissionsService getpermissionservice;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Jwt principal = (Jwt) authentication.getPrincipal();
    	String username=principal.getClaimAsString("preferred_username");
    	System.out.println(username);
    	String token2=request.getHeader("Authorization");
    	System.out.println(token2);
    
    try {
    	String permisosResponse= getpermissionservice.Validate(token2, username);
    	if(permisosResponse==null) {
        	return false; 
         }else {
        	
             ObjectMapper mapper = new ObjectMapper();

            
             String[] arrayFinal = mapper.readValue(permisosResponse, String[].class);

            
             for (String array : arrayFinal) {
            	 authorities.add(new SimpleGrantedAuthority(array));
             }
             
            	    JwtAuthenticationToken token=     new JwtAuthenticationToken(principal, authorities);
                  System.out.println( SecurityContextHolder.getContext().getAuthentication());
                  
                  SecurityContextHolder.getContext().setAuthentication(token);
                  System.out.println( SecurityContextHolder.getContext().getAuthentication());
        		return true;	
	}} catch (Exception e) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}
      
     

    
     
	}
}
