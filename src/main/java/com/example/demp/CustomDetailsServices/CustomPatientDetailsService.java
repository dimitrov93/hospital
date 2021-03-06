package com.example.demp.CustomDetailsServices;

import com.example.demp.CustomDetails.CustomPatientDetails;
import com.example.demp.Entities.Patient;
import com.example.demp.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomPatientDetailsService implements UserDetailsService {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient= patientRepository.findByEmail(username);
        if (patient == null) {
            throw new UsernameNotFoundException("Patient not found");
        }
        return new CustomPatientDetails(patient);
    }
}
