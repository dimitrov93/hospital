package com.example.demp.Repositories;

import com.example.demp.Entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Query("SELECT u FROM Privilege u WHERE u.name = ?1")
    public Privilege findByName(String name);
}
