package com.example.demp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query("SELECT u FROM Doctor u WHERE u.email = ?1")
    public Doctor findByEmail(String email);
}
