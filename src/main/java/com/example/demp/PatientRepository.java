package com.example.demp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query("SELECT u FROM Patient u WHERE u.email = ?1")
    public Patient findByEmail(String email);
}
