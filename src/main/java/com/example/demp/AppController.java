package com.example.demp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("")
    public String viewHomePage(Model model){
        List<User> listUsers = userRepo.findAll();
        List<Doctor> listOfDoctors = doctorRepository.findAll();
        List<Patient> listOfPatients = patientRepository.findAll();
        List<Appointment> listOfAppointments = appointmentRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listOfDoctors", listOfDoctors);
        model.addAttribute("listOfPatients", listOfPatients);
        model.addAttribute("listOfAppointments", listOfAppointments);
        return "index";
    }

    @GetMapping("/logged")
    public String viewHomePage(){
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        List<User> listUsers = userRepo.findAll();
        List<Doctor> listOfDoctors = doctorRepository.findAll();
        List<Patient> listOfPatients = patientRepository.findAll();
        List<Appointment> listOfAppointments = appointmentRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listOfDoctors", listOfDoctors);
        model.addAttribute("listOfPatients", listOfPatients);
        model.addAttribute("listOfAppointments", listOfAppointments);
        return "admin";
    }

    @GetMapping(value="/admin/patients/{order}")
    public String requestMap(Model model, @PathVariable String order){
        if (Objects.equals(order, "asc")) {
            List<Patient> listOfPatients = patientRepository.findAll(Sort.by("firstName"));
            model.addAttribute("listOfPatients", listOfPatients);
        } else if (Objects.equals(order, "desc")) {
            List<Patient> listOfPatients = patientRepository.findAll(Sort.by("firstName").descending());
            model.addAttribute("listOfPatients", listOfPatients);
        }else if (Objects.equals(order, "id")) {
            List<Patient> listOfPatients = patientRepository.findAll(Sort.by("id").descending());
            model.addAttribute("listOfPatients", listOfPatients);
        }
        List<Doctor> listOfDoctors = doctorRepository.findAll();
        List<User> listUsers = userRepo.findAll();
        List<Patient> listOfPatients = patientRepository.findAll();
        List<Appointment> listOfAppointments = appointmentRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listOfDoctors", listOfDoctors);
        model.addAttribute("listOfAppointments", listOfAppointments);
        return "admin";
    }

    @GetMapping(value="/patients/{order}")
    public String sortingPatients(Model model, @PathVariable String order){
        if (Objects.equals(order, "asc")) {
            List<Patient> listOfPatients = patientRepository.findAll(Sort.by("firstName"));
            model.addAttribute("listOfPatients", listOfPatients);
        } else if (Objects.equals(order, "desc")) {
            List<Patient> listOfPatients = patientRepository.findAll(Sort.by("firstName").descending());
            model.addAttribute("listOfPatients", listOfPatients);
        }else if (Objects.equals(order, "id")) {
            List<Patient> listOfPatients = patientRepository.findAll(Sort.by("id").descending());
            model.addAttribute("listOfPatients", listOfPatients);
        }
        List<Doctor> listOfDoctors = doctorRepository.findAll();
        List<User> listUsers = userRepo.findAll();
        List<Patient> listOfPatients = patientRepository.findAll();
        List<Appointment> listOfAppointments = appointmentRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listOfDoctors", listOfDoctors);
        model.addAttribute("listOfAppointments", listOfAppointments);
        return "index";
    }
    @GetMapping("/appointment")
    public String indexHtml(Appointment appointment, Model model) {
        List<Doctor> doctorList = doctorRepository.findAll();
        List<Patient> patientList = patientRepository.findAll();
        model.addAttribute("listDoctors", doctorList);
        model.addAttribute("listPatients", patientList);
        model.addAttribute("appointment", appointment);
        return "appointment";
    }

    @GetMapping("/appointments")
    public String listUsers(Model model) {
        List<Appointment> listOfAppointments = appointmentRepository.findAll();
        model.addAttribute("listOfAppointments", listOfAppointments);
        return "appointments_list";
    }

    @PostMapping("/submition")
    public String postTickets(@Valid Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        appointmentRepository.save(appointment);
        return "redirect:/";
    }

    @GetMapping("/doctorRegForm")
    public String showDoctorRegistrationForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctorRegForm";
    }

    @GetMapping("/patientRegForm")
    public String showPatientRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientRegForm";
    }

    @PostMapping("/process_doctorRegForm")
    public String processDoctorRegister(Doctor doctor) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        doctor.setPassword(encodedPassword);

        Role userRole = roleRepository.findByName("ROLE_DOCTOR");
        Collection<Role> userRoles = Arrays.asList(userRole);
        doctor.setDoctor_roles(userRoles);

        doctorRepository.save(doctor);

        return "redirect:/";
    }

    @PostMapping("/process_patientRegForm")
    public String processPatientRegister(Patient patient) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(encodedPassword);

        Role userRole = roleRepository.findByName("ROLE_PATIENT");
        Collection<Role> userRoles = Arrays.asList(userRole);
        patient.setPatient_roles(userRoles);

        patientRepository.save(patient);

        return "redirect:/";
    }

    // Doctor edit and delete buttons ----- Start
    @GetMapping("/doctorRegForm-edit/{id}")
    public String doctorEditForm(@PathVariable int id, Model model) {
        Doctor doctor = doctorRepository.getById(id);
        model.addAttribute("doctorEdit", doctor);
        return "doctorRegForm-edit";
    }


    @PostMapping("/doctorRegForm-update/{id}")
    public String postEditedDoctor(@PathVariable int id, Doctor doctor) {
        doctor.setId(id);
        doctorRepository.save(doctor);
        return "redirect:/";
    }

    @PostMapping("/delete-doctor/{id}")
    public String deleteDoctor(@PathVariable int id) {
        Doctor doctor = doctorRepository.getById(id);
        doctorRepository.delete(doctor);
        return "redirect:/";
    }

    // Doctor edit and delete buttons ----- END

    // Patient edit and delete buttons ----- Start
    @GetMapping("/patientRegForm-edit/{id}")
    public String patientEditForm(@PathVariable int id, Model model) {
        Patient patient = patientRepository.getById(id);
        model.addAttribute("patientEdit", patient);
        return "patientRegForm-edit";
    }

    @PostMapping("/patientRegForm-update/{id}")
    public String postEditedPatient(@PathVariable int id, Patient patient) {
        patient.setId(id);
        patientRepository.save(patient);
        return "redirect:/";
    }

    @PostMapping("/delete-patient/{id}")
    public String deletePatient(@PathVariable int id) {
        Patient patient = patientRepository.getById(id);
        patientRepository.delete(patient);
        return "redirect:/";
    }

    // Patient edit and delete buttons ----- END

    // Appointment edit and delete buttons ----- Start
    @GetMapping("/app-edit/{id}")
    public String appEditForm(@PathVariable int id, Model model, Appointment appointmentList) {
        List<Doctor> doctorList = doctorRepository.findAll();
        model.addAttribute("listDoctors", doctorList);
        model.addAttribute("appointment", appointmentList);
        Appointment appointment = appointmentRepository.getById((long) id);
        model.addAttribute("appEdit", appointment);
        return "appointment-edit";
    }


    @PostMapping("/app-update/{id}")
    public String postAppEdit(@PathVariable int id, Appointment appointment) {
        appointment.setId(id);
        appointmentRepository.save(appointment);
        return "redirect:/";
    }

    @PostMapping("/app-delete/{id}")
    public String deleteAppointment(@PathVariable int id) {
        Appointment appointment = appointmentRepository.getById((long) id);
        appointmentRepository.delete(appointment);
        return "redirect:/";
    }
    // Appointment edit and delete buttons ----- END

}
