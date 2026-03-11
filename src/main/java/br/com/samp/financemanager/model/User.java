package br.com.samp.financemanager.model;

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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static br.com.samp.financemanager.model.enums.UserStatus.ACTIVE;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private UserStatus status = ACTIVE;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Setter(value = lombok.AccessLevel.NONE)
    @OneToMany(mappedBy = "holder")
    private Set<Account> accounts = new HashSet<>();

    @Setter(value = lombok.AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private Set<Expense> expenses = new HashSet<>();

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
}
