package com.LoginSign.Service;

import com.LoginSign.Entity.UserInfo;
import com.LoginSign.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    // 🔥 Load user for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo user = repository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + username)
                );

        // ✅ Use custom UserDetails class
        return new UserInfoDetails(user);
    }

    // 🔥 Register new user
    public String addUser(UserInfo userInfo) {

        // Encode password before saving
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));

        repository.save(userInfo);

        return "User added successfully!";
    }
}