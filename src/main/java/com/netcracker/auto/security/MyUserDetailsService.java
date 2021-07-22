package com.netcracker.auto.security;

import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        UserDetails user1 = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER").build();
        return user1;

//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword().toLowerCase(), true, true,
//                true, true, user.getAuthorities());//getAuthorities(user.getRoles()));
    }

}
