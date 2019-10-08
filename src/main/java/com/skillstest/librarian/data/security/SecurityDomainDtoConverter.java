package com.skillstest.librarian.data.security;

import com.skillstest.librarian.data.security.User;
import com.skillstest.librarian.data.security.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityDomainDtoConverter {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public SecurityDomainDtoConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User dtoToDomain (UserDto dto) {
        User user = new User (
                dto.getEmail(),
                dto.isEncrypted() ? dto.getPassword() : passwordEncoder.encode(dto.getPassword()),
                dto.getFirstName(),
                dto.getLastName()
        );
        if (dto.getId() != null) user.setId(dto.getId());
        dto.getRoles().stream().forEach(r -> user.addUserRole(r));
        return user;
    }

    public UserDto domainToDto (User p) {
        UserDto dto = new UserDto(
                p.getEmail(),
                p.getPassword(),
                p.getFirstName(),
                p.getName(),
                true);
        dto.setRoles(p.getRoles());
        dto.setId(p.getId());
        return dto;
    }
}
