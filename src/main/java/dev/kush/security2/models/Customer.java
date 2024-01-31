package dev.kush.security2.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer implements UserDetails {

    @Id  @GeneratedValue
    private Long customerId;
    private String name;
    private String email;
    private String pass;

    @Enumerated(EnumType.STRING)
    private CustomerRole role;
    private String phone;
    private Boolean isLocked = false;
    private Boolean isEnable = false;

    public Customer(String name, String email, String pass, CustomerRole role, String phone) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.role = role;
        this.phone = phone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
