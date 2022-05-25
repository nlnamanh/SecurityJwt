package com.hit.securityjwt.controller;

import com.hit.securityjwt.dao.User;
import com.hit.securityjwt.exceptions.BadRequestException;
import com.hit.securityjwt.filters.AuthenticationResponse;
import com.hit.securityjwt.payload.AuthenticationRequest;
import com.hit.securityjwt.repository.UserRepository;
import com.hit.securityjwt.service.MyUserDetailService;
import com.hit.securityjwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Incorrect username or password");
        }
        final UserDetails userDetails = myUserDetailService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());

        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        return ResponseEntity.ok().body(new AuthenticationResponse(jwt, user.getId(), user.getUsername()));
    }
}
