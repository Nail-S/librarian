package com.skillstest.librarian.data.security;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 6788834660066273637L;
    @Id
 //   @GeneratedValue( strategy = GenerationType.AUTO )
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Version
    @Column ( name = "rec_version" )
    private int version;
    @Column ( name = "first_name")
    private String firstName;
    @Column ( name = "last_name")
    private String lastName;
    private String password;
    @NaturalId
    @Column(name = "email", unique = true)
    private String email;
    @JsonBackReference
    @OneToMany ( mappedBy = "user",
                 cascade = CascadeType.ALL,
                orphanRemoval = true )
    private Set<UserRole> roles = new HashSet<>();

    protected User() {
    }
    public User (String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return lastName;
    }

    public void setName(String name) {
        this.lastName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void addUserRole (UserRole role) {
        roles.add(role);
        role.setUser(this);
    }

    public void removeUserRole (UserRole role) {
        roles.remove(role);
        role.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
