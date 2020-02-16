package com.example.demo.configure;

import com.example.demo.POJO.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public static JwtUser create(User user){
        return new JwtUser(
          user.getId(),
          user.getUsername(),
          user.getPassword(),
          user.getEmail(),
          user.getPhoneNumber(),
          mapToGrantedAuthorities(user.getRoles()),
          user.getLastPasswordResetDate(),
          user.getLastUsernameResetDate()
        );
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities){
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
