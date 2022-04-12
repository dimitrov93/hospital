package com.example.demp;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 45, name = "first_name")
    private String firstName;

    @Column(nullable = false, length = 45, name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "patient")
    private Collection<Appointment> appointments;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "patients_roles",
            joinColumns = @JoinColumn(
                    name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "patient_role_id", referencedColumnName = "id"))
    private Collection<Role> patient_roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Collection<Role> getPatient_roles() {
        return patient_roles;
    }

    public void setPatient_roles(Collection<Role> patient_roles) {
        this.patient_roles = patient_roles;
    }
}
