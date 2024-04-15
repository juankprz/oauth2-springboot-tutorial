package com.example.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


@RestController
@RequestMapping("/resources")
public class ResourceController {
	@Autowired
	 GetPermissionsService getpermissionservice;

    @GetMapping("/user")
    public ResponseEntity<String> user() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Jwt principal = (Jwt) authentication.getPrincipal(); 
    	System.out.println(principal.getClaims());
    	 
    	Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ONLY"));
    	    JwtAuthenticationToken token=     new JwtAuthenticationToken(principal, authorities);
          System.out.println( SecurityContextHolder.getContext().getAuthentication());
          
          SecurityContextHolder.getContext().setAuthentication(token);
          System.out.println( SecurityContextHolder.getContext().getAuthentication());
    	 String currentUserName = principal.getClaimAsString("sub");
         return new ResponseEntity<String>(currentUserName, HttpStatus.OK);
    }
    @GetMapping("/user2")
    @PreAuthorize("hasAuthority('get:saludo')")
    public ResponseEntity<String> user2(Authentication authentication) {
    	System.out.println(authentication.getAuthorities());
        return ResponseEntity.ok(authentication.getAuthorities() + " access");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin( jakarta.servlet.http.HttpServletRequest request) {
    	String token=request.getHeader("Authorization");
    	System.out.println(getpermissionservice.Validate(token,"admin"));
        return getpermissionservice.Validate(token,"admin");
    }
}
