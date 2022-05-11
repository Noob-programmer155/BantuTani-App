package com.team1.tm.bantutani.app.configuration.security;

import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class MainUserService implements UserDetailsService {
    private UserRepo userRepo;
    MainUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if(user.isPresent()) {
            User usr = user.get();
            return new MainUserDetail.Builder().
                    name(usr.getUsername()).
                    password(usr.getPassword()).
                    authorities(Arrays.asList(usr.getStatus())).build();
        }
        return null;
    }
}
