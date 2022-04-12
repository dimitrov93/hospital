package com.example.demp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomDoctorDetailsService implements UserDetailsService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(username);
        if (doctor == null) {
            throw new UsernameNotFoundException("Doctor not found");
        }
        return new CustomDoctorDetails(doctor);
    }
}
