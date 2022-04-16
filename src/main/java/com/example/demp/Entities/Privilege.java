package com.example.demp.Entities;

import com.example.demp.Entities.Role;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "userPrivileges")
    private Collection<Role> userRoles;

    @ManyToMany(mappedBy = "doctorPrivileges")
    private Collection<Role> doctorRoles;

    @ManyToMany(mappedBy = "patientPrivileges")
    private Collection<Role> patientRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public Collection<Role> getDoctorRoles() {
        return doctorRoles;
    }

    public void setDoctorRoles(Collection<Role> doctorRoles) {
        this.doctorRoles = doctorRoles;
    }

    public Collection<Role> getPatientRoles() {
        return patientRoles;
    }

    public void setPatientRoles(Collection<Role> patientRoles) {
        this.patientRoles = patientRoles;
    }
}