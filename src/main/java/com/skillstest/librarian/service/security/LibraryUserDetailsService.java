package com.skillstest.librarian.service.security;

import com.skillstest.librarian.data.security.Roles;
import com.skillstest.librarian.data.security.User;
import com.skillstest.librarian.data.security.UserPrincipal;
import com.skillstest.librarian.data.security.UserRole;
import com.skillstest.librarian.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class LibraryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public LibraryUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsersByEmail(username);
        if (user == null) throw new UsernameNotFoundException("Unknown user " + username);
        return new UserPrincipal(user);
    }

    @Transactional
    public void initUsers() {
        System.out.println("Checking the number of application users");
        long count = userRepository.count();
        System.out.println("The number of users found in the database is " + count);
        if (count > 0) return;
        System.out.println("Creating new ones!");
        User librarian = new User("librarian@movie.com", passwordEncoder.encode("12345"), "Flynn", "Carsen");
        librarian.addUserRole(new UserRole(Roles.ADMIN));
        User spectator = new User("spectator@company.com", passwordEncoder.encode("12345"), "spectator", "independent");
        User director = new User ("director@movie.com", passwordEncoder.encode("12345"), "Peter", "Winther");
        director.addUserRole(new UserRole(Roles.READER));
        User writer = new User ("writer@movie.com", passwordEncoder.encode("12345"), "David", "Titcher");
        writer.addUserRole(new UserRole(Roles.WRITER));
        User editor = new User ("editor@movie.com", passwordEncoder.encode("12345"), "Ron", "Rosen");
        editor.addUserRole(new UserRole(Roles.EDITOR));
        userRepository.saveAll(Arrays.asList(librarian, spectator, director, writer, editor));
    }
}
