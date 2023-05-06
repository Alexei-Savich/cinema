package com.example.validation.services;

import com.example.validation.entities.StaffWorker;
import com.example.validation.repositories.StaffWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class StaffWorkerService implements UserDetailsService {

    private final StaffWorkerRepository staffWorkerRepository;

    @Autowired
    public StaffWorkerService(StaffWorkerRepository staffWorkerRepository) {
        this.staffWorkerRepository = staffWorkerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        StaffWorker user = staffWorkerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

}
