package com.example.demp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.Doc;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private  AppointmentRepository appointmentRepository;

    @GetMapping("")
    public String viewHomePage(Model model){
        List<User> listUsers = userRepo.findAll();
        List<Doctor> listOfDoctors = doctorRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listOfDoctors", listOfDoctors);
        return "index";
    }

    @GetMapping("/logged")
    public String viewHomePage(){
        return "logged";
    }

    @GetMapping("/appointment")
    public String indexHtml(Appointment appointment, Model model) {
        model.addAttribute("appointment", appointment);
        return "appointment";
    }

    @GetMapping("/appointments")
    public String listUsers(Model model) {
        List<Appointment> listOfAppointments = appointmentRepository.findAll();
        model.addAttribute("listOfAppointments", listOfAppointments);
        return "appointments";
    }

    @PostMapping("/submition")
    public String postTickets(@Valid Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        appointmentRepository.save(appointment);
        return "congrats";
    }

    @GetMapping("/doctorRegForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctorRegForm";
    }

    @PostMapping("/process_doctorRegForm")
    public String processRegister(Doctor doctor) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        doctor.setPassword(encodedPassword);

        Role userRole = roleRepository.findByName("ROLE_DOCTOR");
        Collection<Role> userRoles = Arrays.asList(userRole);
        doctor.setRoles(userRoles);

        doctorRepository.save(doctor);

        return "congrats";
    }
}
