package com.example.demo.configure;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;


public class JwtUser implements UserDetails {
    private final String id;
    private final String username;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Date lastPasswordResetDate;
    private final Date lastusernameResetDate;

    public JwtUser(
            String id,
            String username,
            String password,
            String email,
            String phoneNumber,
            Collection<? extends GrantedAuthority> authorities,
            Date lastPasswordResetDate,
            Date lastusernameResetDate
    ){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.lastusernameResetDate = lastusernameResetDate;
    }

    public String getId(){return  id;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    //密码是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账户是否过期
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    //账户是否被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //账户是否激活
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Date getLastPasswordResetDate(){
        return lastPasswordResetDate;
    }
    public Date getLastusernameResetDate() {return lastusernameResetDate;}
}
