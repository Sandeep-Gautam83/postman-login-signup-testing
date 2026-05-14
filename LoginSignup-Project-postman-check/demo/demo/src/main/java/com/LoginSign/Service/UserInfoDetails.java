package com.LoginSign.Service;

import com.LoginSign.Entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private String username;   // email used as username
    private String password;
    private List<GrantedAuthority> authorities;

    // Constructor
    public UserInfoDetails(UserInfo userInfo) {

        this.username = userInfo.getEmail();
        this.password = userInfo.getPassword();

        // Convert roles (String -> List<GrantedAuthority>)
        this.authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(role -> role.trim()) // remove extra spaces
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Return roles/authorities
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Return username (email)
    @Override
    public String getUsername() {
        return username;
    }

    // Return password
    @Override
    public String getPassword() {
        return password;
    }

    // Account not expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Account not locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Credentials not expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Account enabled
    @Override
    public boolean isEnabled() {
        return true;
    }
}