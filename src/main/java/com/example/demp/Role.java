package com.example.demp;

import javax.persistence.*;
import javax.print.Doc;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "user_roles")
    private Collection<User> users;

    @ManyToMany(mappedBy = "doctor_roles")
    private Collection<Doctor> doctors;

    @ManyToMany(mappedBy = "patient_roles")
    private Collection<Patient> patients;

    @ManyToMany
    @JoinTable(
            name = "user_roles_privileges",
            joinColumns = @JoinColumn(
                    name = "user_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> userPrivileges;

    @ManyToMany
    @JoinTable(
            name = "doctor_roles_privileges",
            joinColumns = @JoinColumn(
                    name = "doctor_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "doctor_privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> doctorPrivileges;

    @ManyToMany
    @JoinTable(
            name = "patient_roles_privileges",
            joinColumns = @JoinColumn(
                    name = "patient_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "patient_privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> patientPrivileges;

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

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<Privilege> getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(Collection<Privilege> userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    public Collection<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Collection<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Collection<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Collection<Patient> patients) {
        this.patients = patients;
    }

    public Collection<Privilege> getDoctorPrivileges() {
        return doctorPrivileges;
    }

    public void setDoctorPrivileges(Collection<Privilege> doctorPrivileges) {
        this.doctorPrivileges = doctorPrivileges;
    }

    public Collection<Privilege> getPatientPrivileges() {
        return patientPrivileges;
    }

    public void setPatientPrivileges(Collection<Privilege> patientPrivileges) {
        this.patientPrivileges = patientPrivileges;
    }
}
