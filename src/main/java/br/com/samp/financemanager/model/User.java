package br.com.samp.financemanager.model;

import br.com.samp.financemanager.model.enums.UserRole;
import br.com.samp.financemanager.model.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static br.com.samp.financemanager.model.enums.UserRole.ADMIN;
import static br.com.samp.financemanager.model.enums.UserStatus.ACTIVE;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private UserRole role;

    private Instant lockUntil;

    private Integer failedLoginAttempts = 0;

    @Enumerated(EnumType.STRING)
    private UserStatus status = ACTIVE;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "holder")
    private Set<Account> accounts = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private Set<Expense> expenses = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private Set<Earning> earnings = new HashSet<>();

    public void registerFailedLoginAttempt() {
        this.failedLoginAttempts++;

        if (failedLoginAttempts >= 5) {
            this.setLockUntil(Instant.now().plus(15, ChronoUnit.MINUTES));
        }
    }

    public long getRemainingLockSeconds(){
        return Duration.between(Instant.now(),lockUntil).toSeconds();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == ADMIN) {
            return Arrays.stream(UserRole.values())
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole()))
                    .toList();
        }

        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return lockUntil == null || Instant.now().isAfter(lockUntil);
    }

    @Override
    public boolean isEnabled() {
        return status == ACTIVE;
    }
}
