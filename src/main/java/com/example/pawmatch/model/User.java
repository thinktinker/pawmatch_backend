package com.example.pawmatch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                                                         // Integer Id (Auto-increment)

    @Column(nullable = false)
    @NotBlank(message = "First name must not be empty.")
    @Size(min = 2, message = "Minimum 2 characters for first name.")
    private String firstName;                                                   // String firstName

    @Column(nullable = false)
    @NotBlank(message = "Last name must not be empty.")
    @Size(min = 2, message = "Minimum 2 characters for last name.")
    private String lastName;                                                    // String lastName

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email must not be empty.")
    @Email(regexp = "^(?!\\.)(?!.*\\.{2})[a-zA-Z0-9._%+-]+(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
           flags = Pattern.Flag.CASE_INSENSITIVE,
           message = "Email is not valid."
    )
    private String email;                                                       // String Email

    @Column
    @Pattern(
            regexp = "^$|^[689]\\d{7}$",
            message = "Phone number is optional, but must be 8 digits, starting with 6, 8, or 9."
    )
    private String phone;                                                       // String Phone

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EnumHousingType housingType = EnumHousingType.HDB;                  // Enum Housing Type

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EnumExperienceLevel experienceLevel = EnumExperienceLevel.NONE;     // String Housing Type

    @Column
    private String imageUrl;                                                    // String Image URL

    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    // ^ start
    // At least:
    // - one digit [0-9]
    // - one lower-case [a-z]
    // - one upper-case [A-Z]
    // - one special character [@#$%^&+=]
    // - no white spaces \\S+$
    // - 8 characters long {8,}
    // $ end
    @Column(nullable = false)
    @NotBlank(message = "Password cannot be blank.")                            // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Please use a strong password.")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EnumRole role = EnumRole.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    // This puts a heavy load on the User entity when rendering the results
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // List<Feedback> feedbacks = new ArrayList<>();

    // Empty Constructor - covered by Lombok @NoArgsConstructor
    // All Arguments constructor - covered by Lombok
    // Parameterised Constructor - removed
    // Getters and Setters - covered by Lombok @Getter and @Setter

}
