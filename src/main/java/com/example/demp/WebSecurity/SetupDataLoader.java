package com.example.demp.WebSecurity;

import com.example.demp.Entities.Privilege;
import com.example.demp.Entities.Role;
import com.example.demp.Entities.User;
import com.example.demp.Repositories.PrivilegeRepository;
import com.example.demp.Repositories.RoleRepository;
import com.example.demp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import java.util.Collection;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = true;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        Role admin = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_DOCTOR", Arrays.asList(writePrivilege));
        createRoleIfNotFound("ROLE_PATIENT", Arrays.asList(writePrivilege));

        User Cvetomir = new User();
        Cvetomir.setFirstName("Cvetomir");
        Cvetomir.setLastName("Dimitrov");
        Cvetomir.setPassword(passwordEncoder.encode("Ceko"));
        Cvetomir.setEmail("Ceko@dimitrov.com");
        Cvetomir.setUser_roles(Arrays.asList(admin));
        userRepository.save(Cvetomir);

        User Daniel = new User();
        Daniel.setFirstName("Daniel");
        Daniel.setLastName("Boykov");
        Daniel.setPassword(passwordEncoder.encode("Dani"));
        Daniel.setEmail("Dani@boykov.com");
        Daniel.setUser_roles(Arrays.asList(admin));
        userRepository.save(Daniel);
    }

    @Transactional
    Privilege createPrivilegeIfNotFound (String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setDoctorPrivileges(privileges);
            role.setPatientPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
