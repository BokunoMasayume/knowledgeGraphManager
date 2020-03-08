package com.example.demo.serviceImpl;

import com.example.demo.POJO.User;
import com.example.demo.configure.JwtUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.util.JwtTokenUtil;

import com.example.demo.configure.JwtUserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    @Qualifier("myUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.tokenHeader}")
    private String tokenHead;

    @Override
    public User register(User userToAdd) {
        final String username = userToAdd.getUsername();
        final String phonenum = userToAdd.getPhoneNumber();
        final String email = userToAdd.getEmail();
        if(username == null || userRepository.findByUsername(username) !=null){
            return null;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        System.out.println("password is "+rawPassword);
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        userToAdd.setLastUsernameResetDate(userToAdd.getLastPasswordResetDate());
        userToAdd.setRoles(Arrays.asList("ROLE_USER"));

        return userRepository.save(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username , password);
        final Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //if not right username and password , authentication will fail , user will not receive jwt generated

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwtToken = jwtTokenUtil.generateToken(userDetails);
        return jwtToken;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);

        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if(jwtTokenUtil.canTokenBeRefreshed(token , user.getLastPasswordResetDate() , user.getLastusernameResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public User getSelfInfo(String dressToken){
        final String token = dressToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);

        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if(jwtTokenUtil.canTokenBeRefreshed(token , user.getLastPasswordResetDate() , user.getLastusernameResetDate())){
            return userRepository.findByUsername(user.getUsername());
        }
        return null;
    }

}
