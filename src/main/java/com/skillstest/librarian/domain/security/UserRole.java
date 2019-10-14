package com.skillstest.librarian.domain.security;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_roles")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserRole implements Serializable {
   private static final long serialVersionUID = 622464057909335446L;
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long id;
   private String name;
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn ( name = "user_id" )
   private User user;

   protected UserRole(){

   }
    public UserRole (Roles role) {
       name = role.getPersistedTitle();
    }
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return name.equals(userRole.name) &&
                user.equals(userRole.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user);
    }
}
